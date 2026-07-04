// sockjs-client asume que existe el `global` de Node.js, que Vite/esbuild no
// polyfillan para el navegador. Sin esto, cualquier import que toque
// sockjs-client revienta con "ReferenceError: global is not defined" y se
// lleva de encuentro el bootstrap completo de la app (pantalla en blanco).
(window as unknown as { global: typeof window }).global = window;
