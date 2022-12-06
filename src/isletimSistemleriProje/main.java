package isletimSistemleriProje;
/*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
*/
import java.io.*;

import isletimSistemleriProje.Proses.Status;

public class main 
{
	static int sayac=0;
	static int toplamSatirSayisi=0;
	static int ilkVaris=0;
	static int kacinciSatirdaKaldik=0; // bu daha yap�lmad�
	static int gecenSure=0; // bu daha yap�lmad�
	static int realTimeKuyrukElemanSayisi=0, birOncelikliKuyrukElemanSayisi=0,
			ikiOncelikliKuyrukElemanSayisi=0, ucOncelikliKuyrukElemanSayisi=0;
	static Kuyruk realTimeKuyruk,birOncelikliKuyruk,ikiOncelikliKuyruk,ucOncelikliKuyruk;
	static int realTimeKuyrukIlkCount=0;
	static int ilkWhileOkuduguSatir=0, ikinciWhileOkuduguSatir=0;
	
	
	static void txtIlkOkuma() throws IOException
	{
		File file = new File("sayilar.txt");
		if(!file.exists()) // okunacak dosya varm�
		{
			return;
		}
		
		FileReader fReader=new FileReader(file);
		String line;
		BufferedReader bReader=new BufferedReader(fReader);
		
		while((line=bReader.readLine()) != null) // satir bitene kadar oku
		{
			if(line.length()<=0 || line==null || line=="") // sat�r bo� mu
			{}
			else
			{
				String[] bol=line.split(","); // bol[0]-bol[1]-bol[2] olustu. 
											  // 0=Var�sDegeri, 1=OncelikDegeri, 2=CalismaSuresi
				// ilkVar�s Degerini al�yoruz
				if(toplamSatirSayisi==0) // ilk satir ise
				{
					ilkVaris=Integer.parseInt(bol[0]);
				}
				
				//RealTimeKuyruk'a atanacak kac adet satir var onun say�s�n� bulal�m
				// javada dizi uzunlugu belirtilmek zorunda
				if(Integer.parseInt(bol[1])==0)
					realTimeKuyrukElemanSayisi++;
				
				//birOncelikliKuyruk'a atanacak kac adet satir var onun say�s�n� bulal�m
				if(Integer.parseInt(bol[1])==1)
					birOncelikliKuyrukElemanSayisi++;
				
				//ikiOncelikliKuyruk'a atanacak kac adet satir var onun say�s�n� bulal�m
				if(Integer.parseInt(bol[1])==2)
					ikiOncelikliKuyrukElemanSayisi++;
				
				//ucOncelikliKuyruk'a atanacak kac adet satir var onun say�s�n� bulal�m
				if(Integer.parseInt(bol[1])==3)
					ucOncelikliKuyrukElemanSayisi++;
				
				//Txt'de ki Toplam sat�r say�s�n� tutan degisken
				
			} // else sonu
			
			toplamSatirSayisi++;
		} // while sonu
	} // txtIlkOkuma sonu
	
	// Fonksiyon kendini tekrar tekrar cagiracagi icin gerekli parametreler verildi.
	static void oku(int oVarisZamaninaKadarOku, int kacinciSatirdaKaldik) throws IOException
	{
		//Sat�r sat�r kontrol edip hangi kuyruga atanmas� gerekiyor o i�lemler ger�ekle�tiriliyor.
		//Satiri ay�rarak degiskenlere at�yoruz
		File file = new File("sayilar.txt");
		if(!file.exists()) // Okunacak dosya var m�, kontrol ediliyor
		{return;}
		
		FileReader fReader=new FileReader(file);
		String line;
		BufferedReader bReader=new BufferedReader(fReader);
		
		FileReader fReader2=new FileReader(file);
		String line2;
		BufferedReader bReader2=new BufferedReader(fReader2);
		
		int i=0;
		while((line=bReader.readLine()) != null) // satir bitene kadar oku
		{
			if(line.length()<=0 || line==null || line=="") // sat�r bo� mu
			{}
			else
			{
				String[] bol=line.split(","); // bol[0],bol[1],bol[2]
				int pid,varisZamani,oncelik,calismaSuresi;
				pid=i;
				varisZamani=Integer.parseInt(bol[0]);
				oncelik=Integer.parseInt(bol[1]);
				calismaSuresi=Integer.parseInt(bol[2]);
				
				/* Bir sonraki satırın varisZamanina bakıyoruz. 
				 * Eger gecenSure'den düsükse gecenSure=oSatırınVarısZamanı dememiz gerekiyor.*/
				while((line2=bReader2.readLine()) != null) //satirBiteneKadarOku
				{
					if(line2.length()<=0 || line2==null || line2=="") // sat�r bos mu
					{}
					else
					{
						if(i>=kacinciSatirdaKaldik)
						{
							if(ikinciWhileOkuduguSatir==(ilkWhileOkuduguSatir+1))
							{
								String[] bol2=line2.split(","); // bol[0],bol[1],bol[2]
								int varisZamani2,oncelik2,calismaSuresi2;
								varisZamani2=Integer.parseInt(bol2[0]);
								oncelik2=Integer.parseInt(bol2[1]);
								calismaSuresi2=Integer.parseInt(bol2[2]);
								if(i==0)
								{}
								if(varisZamani2>(varisZamani+calismaSuresi))
								{
									oVarisZamaninaKadarOku=varisZamani2;
								}
								else
								{}
							}
							ikinciWhileOkuduguSatir++;
						}
					}
				}
				
			
				if(i>=kacinciSatirdaKaldik && varisZamani<=oVarisZamaninaKadarOku)
				{
					//Proses olu�turulup ilgili kuyru�a ekleniyor.
					Proses proses=new Proses(pid, varisZamani, oncelik, calismaSuresi,Status.ready,Renkler.renk[i%7]);
					kacinciSatirdaKaldik=i+1;
					if(oncelik==0)
					{
						if(realTimeKuyrukElemanSayisi!=0) // realTimeKuyruguna eklenecek sat�r varsa
							realTimeKuyruk.insert(proses);
					}
					else if(oncelik==1)
					{
						if(birOncelikliKuyrukElemanSayisi!=0)
							birOncelikliKuyruk.insert(proses);
					}
					else if(oncelik==2)
					{
						if(ikiOncelikliKuyrukElemanSayisi!=0)
							ikiOncelikliKuyruk.insert(proses);
					}
					else if(oncelik==3)
					{
						if(ucOncelikliKuyrukElemanSayisi!=0)
							ucOncelikliKuyruk.insert(proses);
					}
				
				}
			} // else sonu(sat�r kontrol k�sm�ndaki)
			i++;
			ilkWhileOkuduguSatir++;
		} // while sonu

		
		/*__________ Proses kuyru�una g�re, Kuyruk i�lemleri yap�l�yor. ________*/
		
		/* realTimeKuyruk işlemi */
		for(int j=0; j<realTimeKuyruk.count(); j++)
		{
			if(realTimeKuyruk.indexOf(j).calismaSuresi==0) // calismaSuresi=0 ise proses �lm��t�r(i�ini bitirmi�tir)
			{}
			else // proses bitmemiş ise (hala yaşıyorsa)
			{
				int oSatiraKadarOkuDegeri=0;
				if(j==0 && realTimeKuyruk.indexOf(j).durum!=Status.killed) {
					 oSatiraKadarOkuDegeri=realTimeKuyruk.indexOf(j).varisZamani + realTimeKuyruk.indexOf(j).calismaSuresi;
				}
				else {
					 oSatiraKadarOkuDegeri= realTimeKuyruk.indexOf(j).calismaSuresi;
				}
				realTimeKuyruk.indexOf(j).startProses(); //Proses calismaya basladi
				
				gecenSure+=oSatiraKadarOkuDegeri;
				// System.out.print("GSure:"+gecenSure);
				
				// Eger proses realTimeKuyrugunda ise işi bitene kadar çalışır ve sonlanır.
				int prosesId=realTimeKuyruk.indexOf(j).prosesId;
				int prosesinCalismaSuresi=realTimeKuyruk.indexOf(j).calismaSuresi;
				for(int k=0; k<prosesinCalismaSuresi; k++)
				{
					realTimeKuyruk.indexOf(j).ProsesCalistir( realTimeKuyruk.indexOf(j).prosesId ,realTimeKuyruk.indexOf(j).calismaSuresi);	
				}
				
				// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
				realTimeKuyruk.indexOf(j).varisZamani=0;
				realTimeKuyruk.indexOf(j).oncelik=0;
				realTimeKuyruk.indexOf(j).calismaSuresi=0;
				realTimeKuyruk.indexOf(j).terminatedProses();
				
				//Tekrar Txt'den okuma işlemi yapsın ve diğer satırları okuyarak process işlemine devam etsin.
				//System.out.print("Gecensure:"+gecenSure+"\n");
				//oVarisZamaninaKadarOku=gecenSure;
				oku(gecenSure,kacinciSatirdaKaldik);
				//oku(oVarisZamaninaKadarOku,kacinciSatirdaKaldik)
			} // else sonu
		} // for sonu
		/* realTimeKuyruk işlemi bitti*/
		
		
		//System.out.println(i+"değerli proses geçen süre: "+gecenSure);
		
		
		/* birOncelikliKuyruk işlemi */
		for(int p=0; p<birOncelikliKuyruk.count(); p++)
		{
			if(birOncelikliKuyruk.indexOf(p).calismaSuresi==0 && birOncelikliKuyruk.indexOf(p).durum==Status.killed) // calismaSuresi=0 ise proses işlemini bitirmiştir ()
			{}
			else if(birOncelikliKuyruk.indexOf(p).calismaSuresi==1) //gelen proses 1 saniyelik calisma zamanına sahipse calisir ve ölür 
			{
				if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) //eger askiya alinmiş ve bir saniyesi kalmış ise bir şey yapma
				{}
				else // proses yeni gelmiş ve bir saniyelik çalışma süresi varsa çalış ve öldür
				{
					//Proses calismaya basladi // (1saniye)
					birOncelikliKuyruk.indexOf(p).startProses(); 
					gecenSure+=1;
					
					// Eger proses birOncelikliKuyruk ise bir saniye çalışır ve ölür.
					int prosesId=birOncelikliKuyruk.indexOf(p).prosesId;
					int prosesinCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
					birOncelikliKuyruk.indexOf(p).ProsesCalistir(prosesId, prosesinCalismaSuresi);
					
					// Proses ölür
					birOncelikliKuyruk.indexOf(p).varisZamani=0;
					birOncelikliKuyruk.indexOf(p).oncelik=0;
					birOncelikliKuyruk.indexOf(p).calismaSuresi=0;
					birOncelikliKuyruk.indexOf(p).terminatedProses();
				}
			}
			else if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) //proses askıya alınmışsa
			{
				//oncelik degeri arttırılmıştır, bir işlem yapma
			}
			else // proses bitmemiş ise (hala yaşıyorsa ve askıda degilse)
			{
				//Proses calismaya basladi
				birOncelikliKuyruk.indexOf(p).startProses(); 
				gecenSure+=1;
				
				// Eger proses birOncelikliKuyruk ise bir saniye çalışır,askıya alınır ve ikiOncelikliKuyruk'a eklenir.
				int prosesId=birOncelikliKuyruk.indexOf(p).prosesId;
				int prosesinCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
				birOncelikliKuyruk.indexOf(p).ProsesCalistir(prosesId, prosesinCalismaSuresi);
				ikiOncelikliKuyruk.insert(birOncelikliKuyruk.indexOf(p)); // iki öncelikliye eklendi
				
				//Prosesi askıya al
				birOncelikliKuyruk.indexOf(p).prosesAskiyaAlindi();
				
				//Tekrar Txt'den okuma islemi yap
				oku(gecenSure,kacinciSatirdaKaldik);
				
			} // else sonu (proses bitmemiş ise)
		} // for sonu
		/* birOncelikliKuyruk işlemi bitti*/
		
	} // oku sonu
	
	
	public static void main(String[] args) throws IOException
	{
		//Baslangicta Txt'yi oku ve gerekli degiskenlere degerlerini ata
		txtIlkOkuma();
		
		//Öncelik degerleri bir arttigi icin 1'de ki 2'ye geçicek, 2'de ki 3'e geçicek
		ikiOncelikliKuyrukElemanSayisi=birOncelikliKuyrukElemanSayisi+ikiOncelikliKuyrukElemanSayisi;
		ucOncelikliKuyrukElemanSayisi=ikiOncelikliKuyrukElemanSayisi+ucOncelikliKuyrukElemanSayisi;
		
		//Kuyruklar� Olustural�m
		realTimeKuyruk=new Kuyruk(realTimeKuyrukElemanSayisi);
		birOncelikliKuyruk=new Kuyruk(birOncelikliKuyrukElemanSayisi);
		ikiOncelikliKuyruk=new Kuyruk(ikiOncelikliKuyrukElemanSayisi);
		ucOncelikliKuyruk=new Kuyruk(ucOncelikliKuyrukElemanSayisi);
		
		//Simdi prosesler icin txt' okumalar�na basla
		oku(ilkVaris,kacinciSatirdaKaldik);		
		//oku(oVarisZamaninaKadarOku,kacinciSatirdaKaldik)
	
	}

}
