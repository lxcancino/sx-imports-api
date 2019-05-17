package sx.imports.core

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang3.exception.ExceptionUtils

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec

@GrailsCompileStatic
@EqualsAndHashCode(includes=['clave'])
class Empresa {

    String clave

    String nombre

    Direccion direccion

    String rfc

    String regimen

    String numeroDeCertificado

    byte[] certificadoDigital

    byte[] certificadoDigitalPfx

    byte[] llavePrivada

    String passwordPfx

    X509Certificate certificado

    PrivateKey privateKey

    String registroPatronal

    String versionDeCfdi

    String regimenClaveSat

    static embedded = ['direccion']

    static constraints = {
        clave inList: ['IMPAP', 'PAPER']
        nombre minSize: 5, maxSize:200, unique:true
        rfc maxSize: 13
        direccion nullable:true
        numeroDeCertificado blank:true,minSize:1,maxSize:20
        certificadoDigital nullable:true,maxSize:1024*1024 * 2
        certificadoDigitalPfx nullable:true,maxSize:1024*1024 * 2
        llavePrivada nullable:true,maxSize:1024*1024 * 2
        registroPatronal nullable:true, minSize:1, maxSize:20
        versionDeCfdi inList:['3.2','3.3']
        regimenClaveSat nullable:true

    }

    static transients = ['certificado','certificadoPfx','privateKey']

    X509Certificate getCertificado(){

        if(certificadoDigital && !certificado){
            //assert certificadoDigital,'Debe cargar el binario del certificado '
            try {

                log.info('Cargando certificado digital en formato X509')
                CertificateFactory fact= CertificateFactory.getInstance("X.509","BC")
                InputStream is=new ByteArrayInputStream(certificadoDigital)
                certificado = (X509Certificate)fact.generateCertificate(is)
                //certificado.checkValidity()
                //is.closeQuietly();
                is.close();
                this.certificado=certificado
            } catch (Exception e) {
                e.printStackTrace()
                println 'Error tratando de leer certificado en formato X509 :'+ExceptionUtils.getRootCauseMessage(e)
            }


        }

        return certificado;
    }

    String getCertificadoInfo(){
        return "$certificado?.subjectX500Principal"
    }

    PrivateKey getPrivateKey(){
        if(!privateKey && llavePrivada){
            try {
                final byte[] encodedKey=llavePrivada
                PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(encodedKey)
                final  KeyFactory keyFactory=KeyFactory.getInstance("RSA","BC")
                this.privateKey=keyFactory.generatePrivate(keySpec)
            } catch (Exception e) {
                e.printStackTrace()
                println 'Error tratando de leer llave privada :'+ExceptionUtils.getRootCauseMessage(e)
            }

        }
        return privateKey;
    }

    String toString(){
        return "$nombre ($rfc)"
    }
}
