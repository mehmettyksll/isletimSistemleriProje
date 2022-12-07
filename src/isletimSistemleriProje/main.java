package isletimSistemleriProje;

import java.io.*;

import isletimSistemleriProje.Proses.Status;

public class main 
{
	static int sayac=0;
	static int toplamSatirSayisi=0;
	static int ilkVaris=0;
	static int kacinciSatirdaKaldik=0; 
	static int gecenSure=0; 
	static int realTimeKuyrukElemanSayisi=0, birOncelikliKuyrukElemanSayisi=0,
			ikiOncelikliKuyrukElemanSayisi=0, ucOncelikliKuyrukElemanSayisi=0;
	static Kuyruk realTimeKuyruk,birOncelikliKuyruk,ikiOncelikliKuyruk,ucOncelikliKuyruk;
	static int realTimeKuyrukIlkCount=0;
	static int ilkWhileOkuduguSatir=0, ikinciWhileOkuduguSatir=0;
	static String txtYolu;
	
	static void txtIlkOkuma() throws IOException
	{
		File file = new File(txtYolu);
		if(!file.exists()) // okunacak dosya varmı
		{
			return;
		}
		
		FileReader fReader=new FileReader(file);
		String line;
		BufferedReader bReader=new BufferedReader(fReader);
		
		while((line=bReader.readLine()) != null) // satir bitene kadar oku
		{
			if(line.length()<=0 || line==null || line=="") // satır boş mu
			{}
			else
			{
				String[] bol=line.split(","); // bol[0]-bol[1]-bol[2] olustu. 
											  // 0=VarisDegeri, 1=OncelikDegeri, 2=CalismaSuresi
				// ilkVar�s Degerini al�yoruz
				if(toplamSatirSayisi==0) // ilk satir ise
				{
					ilkVaris=Integer.parseInt(bol[0]);
				}
				
				//RealTimeKuyruk'a atanacak kac adet satir var onun sayısını bulalım
				// javada dizi uzunlugu belirtilmek zorunda
				if(Integer.parseInt(bol[1])==0)
					realTimeKuyrukElemanSayisi++;
				
				//birOncelikliKuyruk'a atanacak kac adet satir var onun sayısını bulalım
				if(Integer.parseInt(bol[1])==1)
					birOncelikliKuyrukElemanSayisi++;
				
				//ikiOncelikliKuyruk'a atanacak kac adet satir var onun sayısını bulalım
				if(Integer.parseInt(bol[1])==2)
					ikiOncelikliKuyrukElemanSayisi++;
				
				//ucOncelikliKuyruk'a atanacak kac adet satir var onun sayısını bulalım
				if(Integer.parseInt(bol[1])==3)
					ucOncelikliKuyrukElemanSayisi++;		
			} // else sonu
			
			//Txt'de ki Toplam sat�r say�s�n� tutan degisken
			toplamSatirSayisi++;
		} // while sonu
	} // txtIlkOkuma sonu
	
	// Fonksiyon kendini tekrar tekrar cagiracagi icin gerekli parametreler verildi.
	static void oku(int oVarisZamaninaKadarOku, int kacinciSatirdaKaldik) throws IOException
	{
		//Satır satır kontrol edip hangi kuyruga atanması gerekiyor o islemler gerceklestiriliyor.
				//Satiri ayırarak degiskenlere atıyoruz
		File file = new File(txtYolu);
		if(!file.exists()) // Okunacak dosya var mı, kontrol ediliyor
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
			if(line.length()<=0 || line==null || line=="") // satir bos mu
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
					if(line2.length()<=0 || line2==null || line2=="") // satir bos mu
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
					// Proses oluşturulup ilgili kuyruğa ekleniyor.
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
			} // else sonu(sat�r kontrol kısmındaki)
			i++;
			ilkWhileOkuduguSatir++;
		} // while sonu

		
		/*________________ Proses kuyruğuna göre Kuyruk İşlemleri Yapılıyor.. _______________*/
		
		
		/* realTimeKuyruk işlemi */
		for(int j=0; j<realTimeKuyruk.count(); j++)
		{
			if(realTimeKuyruk.indexOf(j).calismaSuresi==0) // calismaSuresi=0 ise proses ölmüştür(işini bitirmiştir)
			{}
			else // proses bitmemiş ise (hala yaşıyorsa)
			{
				// Eğer proses oluştuğundan itibaren 20 saniye geçtiyse kendi kendine ölüyor
				if((gecenSure-realTimeKuyruk.indexOf(j).varisZamani)>=20)
				{
					// proses zaten ölmüş mü ölmemiş mi bakalım
					if(realTimeKuyruk.indexOf(j).durum==Status.killed) // zaten ölmüşse bir şey yapma
					{}
					else // ölmemiş ise ölsün
					{
						// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
						realTimeKuyruk.indexOf(j).varisZamani=0;
						realTimeKuyruk.indexOf(j).oncelik=0;
						realTimeKuyruk.indexOf(j).calismaSuresi=0;
						realTimeKuyruk.indexOf(j).terminatedProses();
						//oku(gecenSure,kacinciSatirdaKaldik);
					}
				}
				else
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
				} // else sonu(20 saniye geçti mi)
			} // else sonu
		} // for sonu
		/* realTimeKuyruk işlemi bitti*/
		
		
		/* birOncelikliKuyruk işlemi */
		for(int p=0; p<birOncelikliKuyruk.count(); p++)
		{
			// Eğer proses oluştuğundan itibaren 20 saniye geçtiyse kendi kendine ölüyor
			if((gecenSure-birOncelikliKuyruk.indexOf(p).varisZamani)>=20)
			{
				// proses zaten ölmüş mü ölmemiş mi bakalım
				if(birOncelikliKuyruk.indexOf(p).durum==Status.killed) // zaten ölmüşse bir şey yapma
				{}
				else // ölmemiş ise ölsün
				{
					// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
					birOncelikliKuyruk.indexOf(p).varisZamani=0;
					birOncelikliKuyruk.indexOf(p).oncelik=0;
					birOncelikliKuyruk.indexOf(p).calismaSuresi=0;
					birOncelikliKuyruk.indexOf(p).terminatedProses();
					//oku(gecenSure,(kacinciSatirdaKaldik+1));
				}
			}
			else
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
						
						//Tekrar Txt'den okuma islemi yap
						oku(gecenSure,kacinciSatirdaKaldik);
					}
				}
				else if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) //proses askıya alınmışsa
				{
					//oncelik degeri arttırılmıştır, bir işlem yapma
					//Tekrar Txt'den okuma islemi yap
					//oku(gecenSure,kacinciSatirdaKaldik);
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
					
					int varisZamani=birOncelikliKuyruk.indexOf(p).varisZamani;
					int oncelik=2;
					Status durum=Status.ready;
					String renk=birOncelikliKuyruk.indexOf(p).renk;
					int kalanCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
					//System.out.println("kalanCalismaSuresi: "+kalanCalismaSuresi);
					Proses proses=new Proses(prosesId,varisZamani,oncelik,kalanCalismaSuresi,durum,renk);
					ikiOncelikliKuyruk.insert(proses); // iki öncelikliye eklendi
					
					//Prosesi askıya al
					birOncelikliKuyruk.indexOf(p).prosesAskiyaAlindi();
					
					//System.out.println(birOncelikliKuyruk.indexOf(p).calismaSuresi);
					//Tekrar Txt'den okuma islemi yap
					oku(gecenSure,kacinciSatirdaKaldik);
					
				} // else sonu (proses bitmemiş ise)
			}
		} // for sonu
		/* birOncelikliKuyruk işlemi bitti */
		
		
		
		
		/* ikiOncelikliKuyruk işlemi */
		for(int r=0; r<ikiOncelikliKuyruk.count(); r++)
		{
			// Eğer proses oluştuğundan itibaren 20 saniye geçtiyse kendi kendine ölüyor
			if((gecenSure-ikiOncelikliKuyruk.indexOf(r).varisZamani)>=20)
			{
				// proses zaten ölmüş mü ölmemiş mi bakalım
				if(ikiOncelikliKuyruk.indexOf(r).durum==Status.killed) // zaten ölmüşse bir şey yapma
				{}
				else // ölmemiş ise ölsün
				{
					// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
					ikiOncelikliKuyruk.indexOf(r).varisZamani=0;
					ikiOncelikliKuyruk.indexOf(r).oncelik=0;
					ikiOncelikliKuyruk.indexOf(r).calismaSuresi=0;
					ikiOncelikliKuyruk.indexOf(r).terminatedProses();
					//oku(gecenSure,kacinciSatirdaKaldik);
				}
			}
			else // Eğer proses oluşalı 20 saniye olmadıysa
			{
				if(ikiOncelikliKuyruk.indexOf(r).calismaSuresi==0 && ikiOncelikliKuyruk.indexOf(r).durum==Status.killed) // calismaSuresi=0 ise proses işlemini bitirmiştir ()
				{}
				else if(ikiOncelikliKuyruk.indexOf(r).calismaSuresi==1) //gelen proses 1 saniyelik calisma zamanına sahipse calisir ve ölür 
				{
					if(ikiOncelikliKuyruk.indexOf(r).durum==Status.waiting) //eger askiya alinmiş ve bir saniyesi kalmış ise bir şey yapma
					{}
					else // proses yeni gelmiş ve bir saniyelik çalışma süresi varsa çalış ve öldür
					{
						//Proses calismaya basladi // (1saniye)
						ikiOncelikliKuyruk.indexOf(r).startProses(); 
						gecenSure+=1;
						
						// Eger proses ikiOncelikliKuyruk ise bir saniye çalışır ve ölür.
						int prosesId=ikiOncelikliKuyruk.indexOf(r).prosesId;
						int prosesinCalismaSuresi=ikiOncelikliKuyruk.indexOf(r).calismaSuresi;
						ikiOncelikliKuyruk.indexOf(r).ProsesCalistir(prosesId, prosesinCalismaSuresi);
						
						// Proses ölür
						ikiOncelikliKuyruk.indexOf(r).varisZamani=0;
						ikiOncelikliKuyruk.indexOf(r).oncelik=0;
						ikiOncelikliKuyruk.indexOf(r).calismaSuresi=0;
						ikiOncelikliKuyruk.indexOf(r).terminatedProses();
						
						//Tekrar Txt'den okuma islemi yap
						oku(gecenSure,kacinciSatirdaKaldik);
					}
				}
				else if(ikiOncelikliKuyruk.indexOf(r).durum==Status.waiting) //proses askıya alınmışsa
				{
					//oncelik degeri arttırılmıştır, bir işlem yapma
					//Tekrar Txt'den okuma islemi yap
					//oku(gecenSure,kacinciSatirdaKaldik);
				}
				else // proses bitmemiş ise (hala yaşıyorsa ve askıda degilse)
				{
					//Proses calismaya basladi
					ikiOncelikliKuyruk.indexOf(r).startProses(); 
					gecenSure+=1;
					
					// Eger proses ikiOncelikliKuyruk ise bir saniye çalışır,askıya alınır ve ucOncelikliKuyruk'a eklenir.
					int prosesId=ikiOncelikliKuyruk.indexOf(r).prosesId;
					int prosesinCalismaSuresi=ikiOncelikliKuyruk.indexOf(r).calismaSuresi;
					ikiOncelikliKuyruk.indexOf(r).ProsesCalistir(prosesId, prosesinCalismaSuresi);
					int varisZamani=ikiOncelikliKuyruk.indexOf(r).varisZamani;
					int oncelik=3;
					Status durum=Status.ready;
					String renk=ikiOncelikliKuyruk.indexOf(r).renk;
					int kalanCalismaSuresi=ikiOncelikliKuyruk.indexOf(r).calismaSuresi;
					Proses proses=new Proses(prosesId,varisZamani,oncelik,kalanCalismaSuresi,durum,renk);
					ucOncelikliKuyruk.insert(proses); // ucOncelikliKuyruk'a eklendi
					
					//Prosesi askıya al
					ikiOncelikliKuyruk.indexOf(r).prosesAskiyaAlindi();
					
					//Tekrar Txt'den okuma islemi yap
					oku(gecenSure,kacinciSatirdaKaldik);
					
				} // else sonu (proses bitmemiş ise)
			}
		} // for sonu
		/* ikiOncelikliKuyruk işlemi bitti */
		
		
		/* ucOncelikliKuyruk işlemi*/
		for(int s=0; s<ucOncelikliKuyruk.count(); s++)
		{
			// Eğer proses oluştuğundan itibaren 20 saniye geçtiyse kendi kendine ölüyor
			if((gecenSure-ucOncelikliKuyruk.indexOf(s).varisZamani)>=20)
			{
				// proses zaten ölmüş mü ölmemiş mi bakalım
				if(ucOncelikliKuyruk.indexOf(s).durum==Status.killed) // zaten ölmüşse bir şey yapma
				{}
				else // ölmemiş ise ölsün
				{
					// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
					ucOncelikliKuyruk.indexOf(s).varisZamani=0;
					ucOncelikliKuyruk.indexOf(s).oncelik=0;
					ucOncelikliKuyruk.indexOf(s).calismaSuresi=0;
					ucOncelikliKuyruk.indexOf(s).terminatedProses();
					//oku(gecenSure,kacinciSatirdaKaldik);
				}
			}
			else // Eğer proses oluşalı 20 saniye olmadıysa, işlemlerini gerçekleştirsin
			{
				if(ucOncelikliKuyruk.indexOf(s).calismaSuresi==0 && ucOncelikliKuyruk.indexOf(s).durum==Status.killed) // calismaSuresi=0 ise proses işlemini bitirmiştir ()
				{} // proses ölmüştür
				else if(ucOncelikliKuyruk.indexOf(s).calismaSuresi==1) //gelen proses 1 saniyelik calisma zamanına sahipse calisir ve ölür 
				{
						//Proses calismaya basladi // (1saniye)
						ucOncelikliKuyruk.indexOf(s).startProses(); 
						gecenSure+=1;
						
						int prosesId=ucOncelikliKuyruk.indexOf(s).prosesId;
						int prosesinCalismaSuresi=ucOncelikliKuyruk.indexOf(s).calismaSuresi;
						ucOncelikliKuyruk.indexOf(s).ProsesCalistir(prosesId, prosesinCalismaSuresi);
						
						// Proses ölür
						ucOncelikliKuyruk.indexOf(s).varisZamani=0;
						ucOncelikliKuyruk.indexOf(s).oncelik=0;
						ucOncelikliKuyruk.indexOf(s).calismaSuresi=0;
						ucOncelikliKuyruk.indexOf(s).terminatedProses();
						
						//Tekrar Txt'den okuma islemi yap
						oku(gecenSure,kacinciSatirdaKaldik);
				}
				else // proses bitmemiş ise (hala yaşıyorsa/ölmediyse)
				{
					//Proses calismaya basladi
					ucOncelikliKuyruk.indexOf(s).startProses(); 
					gecenSure+=1;
					
					// Bir saniye calistir ve askiya al
					int prosesId=ucOncelikliKuyruk.indexOf(s).prosesId;
					int prosesinCalismaSuresi=ucOncelikliKuyruk.indexOf(s).calismaSuresi;
					ucOncelikliKuyruk.indexOf(s).ProsesCalistir(prosesId, prosesinCalismaSuresi);
					
					//Prosesi askıya al
					ucOncelikliKuyruk.indexOf(s).prosesAskiyaAlindi();
					
					//Tekrar Txt'den okuma islemi yap
					oku(gecenSure,kacinciSatirdaKaldik);
				}
			}
		} // for sonu
		/* ucOncelikliKuyruk işlemi bitti */
		
	} // oku sonu
	
	
	public static void main(String[] args) throws IOException
	{
		if(args.length<=0) // eğer bir parametre girmezse
		{}
		else
		{
			txtYolu=args[0];
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
}
