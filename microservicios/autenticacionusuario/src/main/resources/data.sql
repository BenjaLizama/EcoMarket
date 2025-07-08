INSERT INTO tipo_cuenta (nombre_tipo_cuenta)
SELECT 'Usuario' WHERE NOT EXISTS (
  SELECT 1 FROM tipo_cuenta WHERE nombre_tipo_cuenta = 'Usuario'
);

INSERT INTO tipo_cuenta (nombre_tipo_cuenta)
SELECT 'Logistica' WHERE NOT EXISTS (
  SELECT 2 FROM tipo_cuenta WHERE nombre_tipo_cuenta = 'Logistica'
);

INSERT INTO tipo_cuenta (nombre_tipo_cuenta)
SELECT 'Empleado de ventas' WHERE NOT EXISTS (
  SELECT 3 FROM tipo_cuenta WHERE nombre_tipo_cuenta = 'Empleado de ventas'
);

INSERT INTO tipo_cuenta (nombre_tipo_cuenta)
SELECT 'Gerente' WHERE NOT EXISTS (
  SELECT 1 FROM tipo_cuenta WHERE nombre_tipo_cuenta = 'Gerente'
);

INSERT INTO tipo_cuenta (nombre_tipo_cuenta)
SELECT 'Administrador' WHERE NOT EXISTS (
  SELECT 1 FROM tipo_cuenta WHERE nombre_tipo_cuenta = 'Administrador'
);
