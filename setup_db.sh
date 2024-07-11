#!/bin/bash

DB_USER="root"
DB_PASSWORD=""
DB_HOST="localhost"
DB_PORT="3306"

mysql -u$DB_USER -p$DB_PASSWORD -h$DB_HOST -P$DB_PORT < schema.sql

echo "Base de datos y tabla creadas exitosamente, datos iniciales insertados."