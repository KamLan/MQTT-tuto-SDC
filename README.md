Petite Application Android ayant pour objectif de tester le protocole MQTT

Utilisation d'un container Mosquitto : Broker MQTT

    utilisation d'une image personalisée: 
        https://hub.docker.com/r/kamlando/mosquitto-custom/
        
        username : user
        password : root
        Dockerfile:
        FROM alpine:3.6
        
        LABEL Description="Custom mosquitto-server"
        
        RUN apk add --no-cache bash
        
        RUN apk add --no-cache vim
        
        USER root
        
        RUN apk --no-cache add mosquitto=1.4.12-r0 && \
        mkdir -p /mosquitto/config /mosquitto/data /mosquitto/log && \
        cp /etc/mosquitto/mosquitto.conf /mosquitto/config && \
        chown -R mosquitto:mosquitto /mosquitto && \
        touch /etc/mosquitto/passwd.txt && \
        printf "%s\n" root root | mosquitto_passwd -c /etc/mosquitto/passwd.txt user
        
        RUN echo allow_anonymous false >> /etc/mosquitto/mosquitto.conf \
        && echo password_file /etc/mosquitto/passwd.txt >> /etc/mosquitto/mosquitto.conf \
        && tail /etc/mosquitto/mosquitto.conf \
        && tail /etc/mosquitto/passwd.txt
        
        CMD ["mosquitto", "-c", "/etc/mosquitto/mosquitto.conf"]
        
    Cette image permet de vérifier la sécurisation du broker mosquitto avec authentification
    
        - user: user
        - password: root
        
    L'utilisation d'un outil client MQTT est nécessaire: l'outil MQTTBox disponible sur le webstore 
    de chrome est efficace: https://chrome.google.com/webstore/detail/mqttbox/kaajoficamnjijhkeomgfljpicifbkaf 