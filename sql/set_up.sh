#!/bin/bash

camunda_dir=/docker-entrypoint-initdb.d/camunda

for database in $(echo $CAMUNDA_DATABASES | sed "s/,/ /g"); do
echo "=============================================="
echo "  Setting up database: $database"
echo "=============================================="

mysql -uroot -p$MYSQL_ROOT_PASSWORD << EOF
CREATE database $database;
GRANT ALL ON $database.* TO camunda;
FLUSH PRIVILEGES;

USE $database;

SOURCE $camunda_dir/activiti.mysql.create.engine.sql;
SOURCE $camunda_dir/activiti.mysql.create.history.sql;
SOURCE $camunda_dir/activiti.mysql.create.identity.sql;
SOURCE $camunda_dir/activiti.mysql.create.decision.engine.sql;
SOURCE $camunda_dir/activiti.mysql.create.decision.history.sql;
SOURCE $camunda_dir/activiti.mysql.create.case.engine.sql;
SOURCE $camunda_dir/activiti.mysql.create.case.history.sql;
SOURCE $camunda_dir/customize.sql;
EOF

done

echo "=============================================="
echo "   Done setting up camunda process databases  "
echo "=============================================="
