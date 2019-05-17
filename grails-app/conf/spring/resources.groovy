import sx.imports.core.EmpresaTenantResolver
import sx.security.UserPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)

    empresaTenantResolver(EmpresaTenantResolver)
}
