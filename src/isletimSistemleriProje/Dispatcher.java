package isletimSistemleriProje;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class Dispatcher
{
	static public int sayac=0;
	static public int toplamSatirSayisi=0;
	static public int ilkVaris=0;
 	static public int gecenSure=0; 
	static public int realTimeKuyrukElemanSayisi=0, birOncelikliKuyrukElemanSayisi=0,
			ikiOncelikliKuyrukElemanSayisi=0, ucOncelikliKuyrukElemanSayisi=0;
	static public Kuyruk realTimeKuyruk,birOncelikliKuyruk,ikiOncelikliKuyruk,ucOncelikliKuyruk;
	static public int realTimeKuyrukIlkCount=0;
	static public int ilkWhileOkuduguSatir=0, ikinciWhileOkuduguSatir=0;
	static public int oncekiGecenSurelerdenEnBuyugu=0;
	static public int pid,varisZamani,oncelik,calismaSuresi;
	static public int ilkVarisinOnceligiKimde;
	static public int kacinciSatirdayiz;
	static public boolean ilkCalistirma;
	public static String txtYolu;
	
	public Dispatcher()
	{
		
	}
	
	static void txtIlkOkuma() throws IOException
	{
		txtYolu=main.txtYolu;
		
		File file = new File(txtYolu);
		if(!file.exists()) // okunacak dosya var mi
		{
			return;
		}
		
		FileReader fReader=new FileReader(file);
		String line;
		BufferedReader bReader=new BufferedReader(fReader);
		
		while((line=bReader.readLine()) != null) // satir bitene kadar oku
		{
			if(line.length()<=0 || line==null || line=="") // satir bos mu
			{}
			else
			{
				String[] bol=line.split(","); // bol[0]-bol[1]-bol[2] olustu. 
											  // 0=VarisDegeri, 1=OncelikDegeri, 2=CalismaSuresi
				// ilkVaris Degerini aliyoruz
				if(toplamSatirSayisi==0) // ilk satir ise
				{
					ilkVaris=Integer.parseInt(bol[0]);
					ilkVarisinOnceligiKimde=Integer.parseInt(bol[1]);
					gecenSure=ilkVaris;
				}
				
				//RealTimeKuyruk'a atanacak kac adet satir var onun sayisini bulalim
				// javada dizi uzunlugu belirtilmek zorunda
				if(Integer.parseInt(bol[1])==0)
					realTimeKuyrukElemanSayisi++;
				
				//birOncelikliKuyruk'a atanacak kac adet satir var onun sayisini bulalim
				if(Integer.parseInt(bol[1])==1)
					birOncelikliKuyrukElemanSayisi++;
				
				//ikiOncelikliKuyruk'a atanacak kac adet satir var onun sayisini bulalim
				if(Integer.parseInt(bol[1])==2)
					ikiOncelikliKuyrukElemanSayisi++;
				
				//ucOncelikliKuyruk'a atanacak kac adet satir var onun sayisini bulalim
				if(Integer.parseInt(bol[1])==3)
					ucOncelikliKuyrukElemanSayisi++;		
			} // else sonu
			
			//Txt'de ki Toplam satir sayisini tutan degisken
			toplamSatirSayisi++;
		} // while sonu
		
		
		//Öncelik degerleri bir arttigi icin 1'de ki 2'ye geçicek, 2'de ki 3'e geçicek
		ikiOncelikliKuyrukElemanSayisi=birOncelikliKuyrukElemanSayisi+ikiOncelikliKuyrukElemanSayisi;
		ucOncelikliKuyrukElemanSayisi=ikiOncelikliKuyrukElemanSayisi+ucOncelikliKuyrukElemanSayisi;
		
		//Kuyruklar� Olustural�m
		realTimeKuyruk=new Kuyruk(realTimeKuyrukElemanSayisi);
		birOncelikliKuyruk=new Kuyruk(birOncelikliKuyrukElemanSayisi);
		ikiOncelikliKuyruk=new Kuyruk(ikiOncelikliKuyrukElemanSayisi);
		ucOncelikliKuyruk=new Kuyruk(ucOncelikliKuyrukElemanSayisi);
		
	} // txtIlkOkuma sonu
	
	
	
	// Fonksiyon kendini tekrar tekrar cagiracagi icin gerekli parametreler verildi.
		static void oku(int oVarisZamaninaKadarOku, int kacinciSatirdaKaldik) throws IOException
		{
			//Satir satir kontrol edip hangi kuyruga atanmasi gerekiyor o islemler gerceklestiriliyor.
			//Satiri ayirarak degiskenlere atıyoruz
			File file = new File(txtYolu);
			if(!file.exists()) // Okunacak dosya var mi, kontrol ediliyor
			{return;}
			
			FileReader fReader=new FileReader(file);
			String line;
			BufferedReader bReader=new BufferedReader(fReader);
			
			FileReader fReader2=new FileReader(file);
			String line2;
			BufferedReader bReader2=new BufferedReader(fReader2);
			
			int i=0;
			ilkWhileOkuduguSatir=0; // Oku fonksiyonu cagirildiginda sifirlanmasi gerekir.
			while((line=bReader.readLine()) != null) // satir bitene kadar oku
			{
				//System.out.println(toplamSatirSayisi);
				/* Txt indisi 0 dan basliyor.
				if(i==3)
				{
					String[] bol=line.split(","); // bol[0],bol[1],bol[2]
					pid=i;
					varisZamani=Integer.parseInt(bol[0]);
					oncelik=Integer.parseInt(bol[1]);
					calismaSuresi=Integer.parseInt(bol[2]);
					System.out.println(varisZamani+","+oncelik+","+calismaSuresi);
				}*/
				if(line.length()<=0 || line==null || line=="") // satir bos mu
				{}
				else // satir bos degilse
				{
					if(i==kacinciSatirdaKaldik && i!=0) // kaldigimiz satiri bul
					{
						/* O satirdaki prosesin bilgilerini aliyoruz ve bir sonraki satirdaki ile karsilastiricaz.*/
						String[] bol=line.split(","); // bol[0],bol[1],bol[2]
						pid=i;
						varisZamani=Integer.parseInt(bol[0]);
						oncelik=Integer.parseInt(bol[1]);
						calismaSuresi=Integer.parseInt(bol[2]);
						
						/* Bir sonraki satirin varisZamanina bakiyoruz. 
						 * Eger gecenSure'den dusukse gecenSure=oSatırınVarısZamanı dememiz gerekiyor.*/
						while((line2=bReader2.readLine()) != null) //satirBiteneKadarOku
						{
							
							if(line2.length()<=0 || line2==null || line2=="") // satir bos mu
							{
								//oVarisZamaninaKadarOku=varisZamani+calismaSuresi;
							}
							else //satir bos degilse
							{
								if(ilkWhileOkuduguSatir+1==toplamSatirSayisi) // O satir yoktur,
								{											  // Txt satir sayisi 0 dan baslar.
									int i0baslangicDegeri=varisZamani;
									oVarisZamaninaKadarOku=i0baslangicDegeri;
								}
								
								else
								{
									
									if(ikinciWhileOkuduguSatir==(ilkWhileOkuduguSatir+1))
									{
										
										String[] bol2=line2.split(","); // bol[0],bol[1],bol[2]
										int varisZamani2,oncelik2,calismaSuresi2;
										varisZamani2=Integer.parseInt(bol2[0]);
										oncelik2=Integer.parseInt(bol2[1]);
										calismaSuresi2=Integer.parseInt(bol2[2]);
										
										int i0baslangicDegeri=varisZamani;
										int i1baslangicDegeri=varisZamani2;
										//System.out.print("k:"+i1baslangicDegeri+"-"+i0baslangicDegeri+"|");
										if(i1baslangicDegeri>i0baslangicDegeri) // o zaman sure desimeliydi
										{
											//System.out.print("k:"+i1baslangicDegeri+"-"+i0baslangicDegeri+"|");

											if(calismaSuresi<(varisZamani2-gecenSure)) {break;}
											gecenSure=i1baslangicDegeri;
											//System.out.print("k:"+gecenSure);
										}
										else // ilk prosesin toplamHarcadigiSure daha buyuk
										{}
										break;
									}
								}
								ikinciWhileOkuduguSatir++;	
							}
						}
						ikinciWhileOkuduguSatir=0; // while bitince kaldigi yeri 0'lamak gerekir.
					} //i==kacinciSatirdaKaldik && i!=0		
					else if(i==kacinciSatirdaKaldik && i==0 && varisZamani<=gecenSure)
					{
						
						// i==0 icin(ilk proses icin kontrol)
						// Proses olusturulup ilgili kuyruga ekleniyor.
						String[] bol=line.split(","); // bol[0],bol[1],bol[2]
						pid=i;
						varisZamani=Integer.parseInt(bol[0]);
						oncelik=Integer.parseInt(bol[1]);
						calismaSuresi=Integer.parseInt(bol[2]);
						
						Proses proses=new Proses(pid, varisZamani, oncelik, calismaSuresi,Status.ready,Renkler.renk[i%7]);
						kacinciSatirdaKaldik=i+1;
						//System.out.println("noldu");
						if(oncelik==0)
						{
							if(realTimeKuyrukElemanSayisi!=0) // realtimeKuyruk'a eklenecek satir varsa
								realTimeKuyruk.insert(proses);
						}
						else if(oncelik==1)
						{
							if(birOncelikliKuyrukElemanSayisi!=0) // birOncelikliKuyruk'a eklenecek satir varsa
								birOncelikliKuyruk.insert(proses);
						}
						else if(oncelik==2)
						{
							if(ikiOncelikliKuyrukElemanSayisi!=0) // ikiOncelikliKuyruk'a eklenecek satir varsa
								ikiOncelikliKuyruk.insert(proses);
						}
						else if(oncelik==3)
						{
							if(ucOncelikliKuyrukElemanSayisi!=0) // ucOncelikliKuyruk'a eklenecek satir varsa
								ucOncelikliKuyruk.insert(proses);
						}
					}
					
					
					if(i>=kacinciSatirdaKaldik && varisZamani<=gecenSure && i!=0)
					{
						
						// Proses olusturulup ilgili kuyruga ekleniyor.
						
						Proses proses=new Proses(pid, varisZamani, oncelik, calismaSuresi,Status.ready,Renkler.renk[i%7]);
						kacinciSatirdaKaldik=i+1;
						if(oncelik==0)
						{
							if(realTimeKuyrukElemanSayisi!=0) // realtimeKuyruk'a eklenecek satir varsa
								realTimeKuyruk.insert(proses);
						}
						else if(oncelik==1)
						{
							if(birOncelikliKuyrukElemanSayisi!=0) // birOncelikliKuyruk'a eklenecek satir varsa
								birOncelikliKuyruk.insert(proses);
						}
						else if(oncelik==2)
						{
							if(ikiOncelikliKuyrukElemanSayisi!=0) // ikiOncelikliKuyruk'a eklenecek satir varsa
								ikiOncelikliKuyruk.insert(proses);
						}
						else if(oncelik==3)
						{
							if(ucOncelikliKuyrukElemanSayisi!=0) // ucOncelikliKuyruk'a eklenecek satir varsa
								ucOncelikliKuyruk.insert(proses);
						}
					}
				} // else sonu(satir kontrol kismindaki)
				i++;
				ilkWhileOkuduguSatir++;		
			} // while sonu

			kacinciSatirdayiz=kacinciSatirdaKaldik;
			
			
			/*________________ Proses kuyruguna gore Kuyruk Islemleri Yapiliyor.. _______________*/
			
			RealTimeKuyrukClass realTimeKuyrukClass=new RealTimeKuyrukClass();
			BirOncelikliKuyrukClass birOncelikliKuyrukClass=new BirOncelikliKuyrukClass();
			IkiOncelikliKuyrukClass ikiOncelikliKuyrukClass=new IkiOncelikliKuyrukClass();
			UcOncelikliKuyrukClass ucOncelikliKuyrukClass=new UcOncelikliKuyrukClass();
			
			//if(kacinciSatirdaKaldik==0)
			if(ilkCalistirma==true)
			{
				if(ilkVarisinOnceligiKimde==0)
				{
					realTimeKuyrukClass.sifirOncelikFonksiyon();
				}
				else if(ilkVarisinOnceligiKimde==1)
				{
					birOncelikliKuyrukClass.birOncelikFonksiyon();
				}
				else if(ilkVarisinOnceligiKimde==2)
				{
					ikiOncelikliKuyrukClass.ikiOncelikFonksiyon();
				}
				else if(ilkVarisinOnceligiKimde==3)
				{
					ucOncelikliKuyrukClass.ucOncelikFonksiyon();
					//System.out.println("burda");
				}
				ilkCalistirma=false;
			}
			else 
			{
				if(!realTimeKuyruk.kuyruktaOkunacakElemanVarmi())
					realTimeKuyrukClass.sifirOncelikFonksiyon();
				if(!birOncelikliKuyruk.kuyruktaOkunacakElemanVarmi())
					birOncelikliKuyrukClass.birOncelikFonksiyon();
				if(!ikiOncelikliKuyruk.kuyruktaOkunacakElemanVarmi())
					ikiOncelikliKuyrukClass.ikiOncelikFonksiyon();
				if(!ucOncelikliKuyruk.kuyruktaOkunacakElemanVarmi())
					ucOncelikliKuyrukClass.ucOncelikFonksiyon();
			}	
		} // oku sonu
		
	
	
}
