databaseChangeLog = {

    changeSet(author: "ruben (generated)", id: "1557248070093-1") {
        createTable(tableName: "producto") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "productoPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "swx", type: "VARCHAR(255)")

            column(name: "calibre", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "ancho", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "gramos", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "caras", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "clave", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "unidad", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "unidad_sat", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "acabado", type: "VARCHAR(255)")

            column(name: "kilos", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "largo", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "marca_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "update_user", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "clase_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "precio_credito", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "m2", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "descripcion", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "precio_contado", type: "DECIMAL(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "clave_prod_sat", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "clave_unidad_sat", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "color", type: "VARCHAR(255)")

            column(name: "linea_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "create_user", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ruben (generated)", id: "1557248070093-2") {
        addUniqueConstraint(columnNames: "clave", constraintName: "UC_PRODUCTOCLAVE_COL", tableName: "producto")
    }

    changeSet(author: "ruben (generated)", id: "1557248070093-3") {
        addForeignKeyConstraint(baseColumnNames: "marca_id", baseTableName: "producto", constraintName: "FK868tnrt85f21kgcvt9bftgr8r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "marca")
    }

    changeSet(author: "ruben (generated)", id: "1557248070093-4") {
        addForeignKeyConstraint(baseColumnNames: "clase_id", baseTableName: "producto", constraintName: "FKk1w0arquk4r96cmn87vh7k5vx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "clase")
    }

    changeSet(author: "ruben (generated)", id: "1557248070093-5") {
        addForeignKeyConstraint(baseColumnNames: "linea_id", baseTableName: "producto", constraintName: "FKp9mg960e99epe6nnfn34ijqh3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "linea")
    }
}
