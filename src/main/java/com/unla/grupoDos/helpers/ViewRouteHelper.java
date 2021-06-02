package com.unla.grupoDos.helpers;

public class ViewRouteHelper {
	
	public final static String INDEX = "inicio/index";

	// --------------- VISTAS AUDITOR -------------------	
	
	public final static String AUDITOR_ROOT = "/auditor/";
	
	public final static String INICIO_AUDITOR = "/auditor/inicio";
	public final static String VISTA_USUARIOS_AUDITOR = "auditor/usuario/listado";
	public final static String VISTA_PERFILES_AUDITOR = "auditor/perfil/listado";
	public final static String VISTA_AUDITOR_DESCARGAR_USUARIOS = "auditor/usuario/descargarListadoUsuarios";
	public final static String VISTA_AUDITOR_DESCARGAR_PERFILES = "/auditor/perfil/descargarListadoPerfiles";
	
	public final static String PREGUNTA_ENTRE_FECHAS = "auditor/permisos/entreFechaYFecha";
	public final static String LISTADO_PERMISOS = "auditor/permisos/listado";
	
	// --------------- VISTAS ADMINISTRADOR -------------------
	
	public final static String ADMINISTRADOR_ROOT = "/admin/";
	public final static String INICIO_ADMIN = "/administrador/inicio";
	
	// Perfiles:
	public final static String ADMINISTRADOR_ROOT_PERFILES = "/admin/perfiles";
	
	public final static String VISTA_PERFIL_NUEVO= "/administrador/perfil/nuevo";
	public final static String VISTA_PERFIL_ACTUALIZAR= "/administrador/perfil/actualizar";
	public final static String VISTA_PERFIL_INDEX = "/administrador/perfil/listado";	
	
	// Usuarios:
	public final static String ADMINISTRADOR_ROOT_USUARIOS = "/admin/usuarios";
	
	public final static String VISTA_USUARIO_NUEVO = "administrador/usuario/nuevo";
	public final static String VISTA_USUARIO_ACTUALIZAR = "administrador/usuario/actualizar";
	public final static String VISTA_USUARIOS_INDEX = "administrador/usuario/listado";
	
	// --------------- VISTAS LOG IN -------------------
	
	public final static String USUARIO_LOGIN = "usuario/login";
	public final static String USUARIO_LOGOUT = "usuario/logout";
	
	// --------------- VISTAS PERMISOS -------------------
	
	public final static String PREGUNTA_PERMISO = "permiso/pregunta";
	public final static String NUEVO_PERMISO = "permiso/nuevo";
	public final static String VER_PERMISO = "permiso/verPermiso";
	
	public final static String PREGUNTA_DIARIO = "permiso/permisoDiario/pregunta";	
	public final static String PREGUNTA_PERIODO = "permiso/permisoPeriodo/pregunta";
	
	
	public final static String PERMISO_NO_ENCONTRADO = "permiso/noExiste";
	
	public final static String PREGUNTA_PERMISO_POR_PERSONA = "permiso/traer/buscarPermiso";
	public final static String LISTADO_PERMISOS_POR_PERSONA = "permiso/traer/listaPermisosPorPersona";
}
