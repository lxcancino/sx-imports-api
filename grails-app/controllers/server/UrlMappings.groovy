package server

class UrlMappings {

    static mappings = {

        // "/"(controller: 'application', action:'index')
        "/api/session"(controller: 'application', action: 'session')
        "/api/empresas"(resources: 'empresa', excludes: ['create', 'edit', 'patch'])

        "/api/lineas"(resources: 'linea', excludes: ["create", "edit","patch"])
        "/api/marcas"(resources: 'marca', excludes: ["create", "edit","patch"])
        "/api/clases"(resources: 'clase', excludes: ["create", "edit","patch"])
        "/api/productos"(resources: 'producto', excludes: ["create", "edit","patch"])
        "/api/clientes"(resources: 'cliente', excludes: ["create","edit","patch"])

        // Proveedores
        "/api/proveedores"(resources: 'proveedor', excludes: ["create", "edit","patch"]){
            "productos"(resources: 'proveedorProducto', excludes: ["create", "edit","patch"])
        }
        "/api/aduanas"(resources: 'aduana', excludes: ["create", "edit", "patch"])

        "/api/compras"(resources: 'compra', excludes: ["create", "edit", "patch"])

        "/api/embarques"(resources: 'embarque', excludes: ["create", "edit", "patch"])
        "/api/pedimentos"(resources: 'embarque', excludes: ["create", "edit", "patch"])
        "/api/cuentasDeGasto"(resources: 'cuentaDeGastos', excludes: ["create", "edit", "patch"])
        "/api/distribuciones"(resources: 'distribucion', excludes: ["create","edit", "patch"]){
            "partidas"(resources: 'distribucionDet',excludes: ["create", "edit", "patch"])
        }

        "/api/cfdi"(resources: 'cfdi', excludes: ["create", "edit", "patch"])
        "/api/cfdiCancelado"(resources: 'cfdi', excludes: ["create", "edit", "patch"])

        "500"(view: '/error')
        "404"(view: '/notFound')
    }

}
