#convert our jks to qip-pyton files
rm -f *.pem *.pkcs12

#Нужно сгенерировать две ключницы jks одна с сертификатом сервера, другая с приватным ключом нашего клиента

#create ca-certificate сервера
keytool -importkeystore -srckeystore truststore.jks -srcstorepass changeit -destkeystore inter.pkcs12 -srcstoretype JKS -deststoretype PKCS12 -deststorepass changeit
openssl pkcs12 -nokeys -passin pass:changeit -in inter.pkcs12 -out ca-certificate.pem
openssl pkcs12 -export -nokeys -in ca-certificate.pem -out ca-certificate.p12 -passin pass:changeit -passout pass:

#create client certificate
keytool -importkeystore -srckeystore keystore.jks -srcstorepass changeit -destkeystore client.pkcs12 -srcstoretype JKS -deststoretype PKCS12 -deststorepass changeit
openssl pkcs12 -nocerts -passin pass:changeit -in client.pkcs12 -nodes -out client-private-key-no-password.pem
openssl pkcs12 -nocerts -passin pass:changeit -in client.pkcs12 -passout pass:client-password -out client-private-key.pem
openssl pkcs12 -nokeys -passin pass:changeit -in client.pkcs12 -out client-certificate.pem

#create client-certificate клиента
openssl pkcs12 -export -out client-certificate.p12 -passout pass:client-password -inkey client-private-key-no-password.pem -in client-certificate.pem -name client-certificate