package server

class UrlMappings {

    static mappings = {
        /*
        "/"(controller: 'application', action:'index')
        "/api/session"(controller: 'application', action: 'session')
        */
        "/api/linea"(resources: 'linea', excludes: ["create","save","edit","patch"])
        "/api/marca"(resources: 'marca', excludes: ["create","save","edit","patch"])
        "/api/clase"(resources: 'clase', excludes: ["create","save","edit","patch"])

        "500"(view: '/error')
        "404"(view: '/notFound')
    }

}
