@ECHO OFF
ECHO ************************************************************
ECHO * INSTALANDO    * SERVICIO WEB                             *
ECHO ************************************************************
PAUSE
ECHO ::
ECHO INGRESANDO A LA RUTA  :: E:\Apps\kenpis\war-install
CD /D C:\Apps\kenpis\war-install
ECHO ::
ECHO UBICADO EN LA RUTA    :: E:\Apps\kenpis\war-install
ECHO ::
ECHO INSTALANDO SERVICIO   :: SERVICIO WEB
kenpis-service-web.exe install
ECHO ::
ECHO ACTIVANDO SERVICIO   :: SERVICIO WEB
NET START "KENPIS SERVICIO WEB"
ECHO ::
ECHO ************************************************************
ECHO * INSTALADO     * SERVICIO WEB                             *
ECHO ************************************************************
PAUSE