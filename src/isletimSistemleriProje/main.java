package isletimSistemleriProje;

import java.io.*;
/*
 * 			  		  SAKARYA UNIVERSITESI 
 * 				BILGISAYAR MUHENDISLIGI BOLUMU 
 * 		  	 	 	ISLETIM SISTEMLERI DERSI
 *  				 	 PROJE ODEVI
 * 					 	 DISTPATCHER
 * 
 * 
 * 			OGRENCILER: MEHMET YUKSEL    B211210305
 * 			  			RESUL  CALISKAN  B191210002
 * 
 * 
 * */

public class main 
{
	static public String txtYolu;

	public static void main(String[] args) throws IOException, InterruptedException
	{
		// Dosyay� jar olarak calistirdigimiz icin parametrenin args[] olarak girilip girilmedi�ini kontrol ediyoruz.
		if(args.length<=0) // Eger bir parametre girmezse
		{}
		else
		{
			txtYolu=args[0]; // jar dosyas�ndan gelen txt degeri al�n�yor.
			
			// G�revlendirici-Dispatcher- class'� olusturuluyor.
			Dispatcher dispatcher=new Dispatcher(txtYolu);
			
			// Baslangicta Txt' bir kez okunuyor. Ve gerekli degiskenlere degerleri atan�yor.(Dizi uzunlugu vb.)
			dispatcher.txtIlkOkuma();
			
			//Simdi prosesler icin txt okuma islemleri basliyor.
			dispatcher.oku(dispatcher.ilkVaris,0); //Hen�z bir okuma yap�lmad��� i�in ilkSatir bilgisi 0 olarak gonderiliyor.		
		}
	}
}
