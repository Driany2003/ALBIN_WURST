@ECHO OFF
ECHO ************************************************************
ECHO * DESINSTALANDO * SERVICIO WEB                             *
ECHO ************************************************************
PAUSE
ECHO ::
ECHO DETENIENDO SERVICIO   :: SERVICIO WEB
NET STOP "KENPIS SERVICIO WEB"
ECHO ::
ECHO INGRESANDO A LA RUTA  :: E:\Apps\kenpis\war-install
CD /D E:\Apps\kenpis\war-install
ECHO ::
ECHO UBICADO EN LA RUTA    :: E:\Apps\kenpis\war-install
ECHO ::
ECHO DESINSTALANDO SERVICIO :: SERVICIO WEB
kenpis-service-web.exe uninstall
ECHO ::
ECHO ************************************************************
ECHO * DESINSTALADO   * SERVICIO WEB                            *
ECHO ************************************************************
PAUSE