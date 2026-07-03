package com.foodgest.users.servicesimplements;

import com.foodgest.pedidos.entities.Pedido;
import com.foodgest.shared.exceptions.BusinessException;
import com.foodgest.users.dtos.MovimientoWalletResponseDto;
import com.foodgest.users.dtos.WalletResponseDto;
import com.foodgest.users.entities.MovimientoWallet;
import com.foodgest.users.entities.Wallet;
import com.foodgest.users.repositories.MovimientoWalletRepository;
import com.foodgest.users.repositories.WalletRepository;
import com.foodgest.users.servicesinterfaces.IWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements IWalletService {

    private final WalletRepository walletRepository;
    private final MovimientoWalletRepository movimientoWalletRepository;

    public WalletServiceImpl(WalletRepository walletRepository,
                             MovimientoWalletRepository movimientoWalletRepository) {
        this.walletRepository = walletRepository;
        this.movimientoWalletRepository = movimientoWalletRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponseDto obtenerPorUsuario(UUID usuarioId) {
        return WalletResponseDto.from(getWalletByUsuario(usuarioId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoWalletResponseDto> listarMovimientos(UUID usuarioId) {
        Wallet wallet = getWalletByUsuario(usuarioId);
        return movimientoWalletRepository.findByWalletIdOrderByCreatedAtDesc(wallet.getId()).stream()
                .map(MovimientoWalletResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void retenerPagoVenta(Pedido pedido) {
        UUID agricultorUsuarioId = pedido.getOferta().getAgricultor().getUsuario().getId();
        Wallet wallet = getWalletByUsuario(agricultorUsuarioId);
        BigDecimal montoNeto = pedido.getSubtotal().subtract(nullToZero(pedido.getComisionPlataforma()));
        BigDecimal anterior = wallet.getSaldoRetenido();
        BigDecimal posterior = anterior.add(montoNeto);

        wallet.setSaldoRetenido(posterior);
        walletRepository.save(wallet);
        registrarMovimiento(wallet, pedido, "escrow_retencion", montoNeto, anterior, posterior,
                "Pago retenido en escrow por pedido " + pedido.getCodigoSeguimiento());
    }

    @Override
    @Transactional
    public void liberarPagoVenta(Pedido pedido) {
        UUID agricultorUsuarioId = pedido.getOferta().getAgricultor().getUsuario().getId();
        Wallet wallet = getWalletByUsuario(agricultorUsuarioId);
        BigDecimal montoNeto = pedido.getSubtotal().subtract(nullToZero(pedido.getComisionPlataforma()));

        if (wallet.getSaldoRetenido().compareTo(montoNeto) < 0) {
            throw new BusinessException("Saldo retenido insuficiente para liberar el pago", HttpStatus.CONFLICT);
        }

        BigDecimal retenidoAnterior = wallet.getSaldoRetenido();
        wallet.setSaldoRetenido(retenidoAnterior.subtract(montoNeto));

        BigDecimal disponibleAnterior = wallet.getSaldoDisponible();
        BigDecimal disponiblePosterior = disponibleAnterior.add(montoNeto);
        wallet.setSaldoDisponible(disponiblePosterior);

        walletRepository.save(wallet);
        registrarMovimiento(wallet, pedido, "escrow_liberacion", montoNeto, disponibleAnterior, disponiblePosterior,
                "Pago liberado por entrega conforme del pedido " + pedido.getCodigoSeguimiento());
    }

    private Wallet getWalletByUsuario(UUID usuarioId) {
        return walletRepository.findByUsuario_Id(usuarioId)
                .orElseThrow(() -> new BusinessException("Wallet no encontrada", HttpStatus.NOT_FOUND));
    }

    private void registrarMovimiento(Wallet wallet, Pedido pedido, String tipo, BigDecimal monto,
                                     BigDecimal saldoAnterior, BigDecimal saldoPosterior, String descripcion) {
        MovimientoWallet movimiento = new MovimientoWallet();
        movimiento.setWallet(wallet);
        movimiento.setPedido(pedido);
        movimiento.setTipo(tipo);
        movimiento.setMonto(monto);
        movimiento.setSaldoAnterior(saldoAnterior);
        movimiento.setSaldoPosterior(saldoPosterior);
        movimiento.setDescripcion(descripcion);
        movimientoWalletRepository.save(movimiento);
    }

    private BigDecimal nullToZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}

