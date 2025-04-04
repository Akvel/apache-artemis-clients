import os
import json

# настраиваем SSL и авторизацию по сертификату
def init_ssl_auth(container):
    container.allowed_mechs='EXTERNAL'

    if os.name == "nt":
        container.ssl.client.set_trusted_ca_db('../ssl/ca-certificate.p12')
        container.ssl.client.set_credentials('../ssl/client-certificate.p12', 'client-certificate', "client-password")
    else:
        container.ssl.client.set_trusted_ca_db('../ssl/ca-certificate.pem')
        container.ssl.client.set_credentials('../ssl/client-certificate.pem',  '../ssl/client-private-key.pem', "client-password")