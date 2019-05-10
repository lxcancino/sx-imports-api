package server

class UrlMappings {

    static mappings = {

        // "/"(controller: 'application', action:'index')
        "/api/session"(controller: 'application', action: 'session')

        "/api/lineas"(resources: 'linea', excludes: ["create","save","edit","patch"])
        "/api/marcas"(resources: 'marca', excludes: ["create","save","edit","patch"])
        "/api/clases"(resources: 'clase', excludes: ["create","save","edit","patch"])
        "/api/productos"(resources: 'producto', excludes: ["create","save","edit","patch"])
        "/api/clientes"(resources: 'cliente', excludes: ["create","save","edit","patch"])
        // Proveedores
        "/api/proveedores"(resources: 'proveedor', excludes: ["create","save","edit","patch"]){
            "productos"(resources: 'proveedorProducto', excludes: ["create","save","edit","patch"])
        }

        "500"(view: '/error')
        "404"(view: '/notFound')
    }

}
