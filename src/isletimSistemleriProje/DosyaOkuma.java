package isletimSistemleriProje;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class DosyaOkuma {
    public static	File file;
	static Kuyruk realTimeKuyruk,birOncelikliKuyruk,ikiOncelikliKuyruk,ucOncelikliKuyruk;
    //ilk satir degerini almak icin bool deger
    static boolean ilkSatir=true;
    static int id,pid,varisZamani,oncelik,calismaSuresi=0;
    
    //constructor da args i file a atadik
    DosyaOkuma(String txt)
    {
    	file= new File(txt);
    }
    
    
    public void Oku(int gecenSure,int kalinanSatir) throws NumberFormatException, IOException 
    {
    	if(!file.exists()) // Okunacak dosya var mi diye kontrol ediliyor
		{return;}
		
		FileReader fReader=new FileReader(file);
		String line;
		BufferedReader bReader=new BufferedReader(fReader);
		
		FileReader fReader2=new FileReader(file);
		String line2;
		BufferedReader bReader2=new BufferedReader(fReader2);
				// txtIlkOkuma sonu
    }
}
