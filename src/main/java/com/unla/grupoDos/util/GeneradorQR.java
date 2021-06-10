package com.unla.grupoDos.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.entities.PermisoPeriodo;
import com.unla.grupoDos.services.IPermisoService;

public class GeneradorQR {
	
	@Autowired
	@Qualifier("permisoService")
	private IPermisoService permisoService;
	
	public GeneradorQR() {}
	public byte[] getQRCodeImage(Permiso permiso) throws WriterException, IOException {
		String url = generarUrl(permiso);
	    QRCodeWriter qrCodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 350, 350);
	    //System.out.println("URL : " + url);
	    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
	    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
	    byte[] pngData = pngOutputStream.toByteArray(); 
	    return pngData;
	}
	
	private String generarUrl(Permiso permiso) {
		String url = "";
		int cont = 0;	
		url = "https://grupodospermiso.netlify.app/index.html?fecha=" + permiso.getFecha().toString() + "&datosPersona=" + permiso.getPedido().toString();
		for(Lugar lugar : permiso.getDesdeHasta()) {
			url += "&datosDesde=" + lugar.toString();
			if(cont == 1)
				url += "&datosHasta=" + lugar.toString();
			cont++;
		}
		
		if(permiso instanceof PermisoDiario)
			url += "&permisoDiario=1&motivo=" + ((PermisoDiario)permiso).getMotivo();
		else {
			int vacaciones = 0;
			if(((PermisoPeriodo)permiso).isVacaciones()) vacaciones = 1;
			url += "&permisoDiario=0&datosRodado=" + ((PermisoPeriodo)permiso).getRodado().toString() +
				"&cantDias=" + ((PermisoPeriodo)permiso).getCantDias() + "&vacaciones=" + vacaciones;
		}
		url = url.replace(" ", "%20");
		return url;
	}
}
