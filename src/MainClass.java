import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MainClass
{
	private static int LUNGHEZZA_RECORD=1000;

	public static void main(String[] args) 
	{
	/*	//Persone inserite nel file persone.txt in ordine alfabetico
	 
		Persona p1=new Persona ("Alfio","333434388","Via x", "Milano");
		Persona p2=new Persona ("Basilio","333434388","Via y", "Torino");
		Persona p3=new Persona ("Claudiano","333214388","Via z", "Roma");
		Persona p4=new Persona ("Davide","333434388","Via x", "Milano");
		Persona p5=new Persona ("Nino","333434388","Via y", "Torino");
		Persona p6=new Persona ("Oreste","333214388","Via z", "Roma");
		Persona p7=new Persona ("Piero","333434388","Via x", "Milano");
		Persona p8=new Persona ("Romolo","333434388","Via y", "Torino");
		Persona p9=new Persona ("Tino","333214388","Via z", "Roma");
			
		try 
		{
			salvaPersonaAppend("persone.txt", p1.toCSV());
			salvaPersonaAppend("persone.txt", p2.toCSV());
			salvaPersonaAppend("persone.txt", p3.toCSV());
			salvaPersonaAppend("persone.txt", p4.toCSV());
			salvaPersonaAppend("persone.txt", p5.toCSV());
			salvaPersonaAppend("persone.txt", p6.toCSV());
			salvaPersonaAppend("persone.txt", p7.toCSV());
			salvaPersonaAppend("persone.txt", p8.toCSV());
			salvaPersonaAppend("persone.txt", p9.toCSV());
		} 
		catch (IOException e) 
		{
			System.err.println("Problemi accesso al file");
		}

	*/	
 
		try
		{
			System.out.println(eliminaRecord("persone.txt", "Davide"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	//aggiunge un record (persona in formato stringa (CSV)) in coda al file
	private static void salvaPersonaAppend(String nomeFile, String persona) throws IOException, FileNotFoundException
	{
		RandomAccessFile raf= new RandomAccessFile(nomeFile, "rw");
		
		for (int i = persona.length(); i < LUNGHEZZA_RECORD; i++) 
			persona+=" ";
		
		Long byteFineFile=raf.length();
		raf.seek(byteFineFile);
		raf.writeBytes(persona);
		raf.close();
	}
	
	//Restituisce il record (persona in formato stringa (CSV)) alla posizione specificata
	private static String leggiFileRandom(String nomeFile,int posizione) throws FileNotFoundException, IOException
	{
		long posizioneByte=(posizione-1)*LUNGHEZZA_RECORD;
		String recordLetto;
		RandomAccessFile raf= new RandomAccessFile(nomeFile, "r");
		raf.seek(posizioneByte);
		recordLetto=raf.readLine();
		return recordLetto;
		
	}
	
	//Restituisce un record (persona in formato stringa (CSV)) ricercato in base al campo "nominativo"
	private static String ricercaBinaria(String nomeFile, String nominativoDaCercare) throws FileNotFoundException, IOException
	{
		File file=new File(nomeFile);
		int lunghezzaFile=(int) file.length();
		
		int inizio=1;
		int fine=lunghezzaFile/LUNGHEZZA_RECORD;
		int posizioneRicerca;
		String recordLetto;
		String[] campiRecord;
		
		while (inizio<=fine) 
		{
			posizioneRicerca=(inizio+fine)/2;
			recordLetto=leggiFileRandom(nomeFile, posizioneRicerca);
			campiRecord=recordLetto.split(";");
			
			if (campiRecord[0].compareTo(nominativoDaCercare)==0)
				return recordLetto;
			else
			{
				if (campiRecord[0].compareTo(nominativoDaCercare)<0)
				{
					inizio=posizioneRicerca+1;
				}
				else
					fine=posizioneRicerca-1;
			}		
		}
		return ("record non presente");
		
	}
	
	//Restituisce la posizione di un record nel file, il record è individuato in base al nominativo
	private static int ricercaBinariaPosizione(String nomeFile, String nominativoDaCercare) throws FileNotFoundException, IOException
	{
		File file=new File(nomeFile);
		int lunghezzaFile=(int) file.length();
		
		int inizio=1;
		int fine=lunghezzaFile/LUNGHEZZA_RECORD;
		int posizioneRicerca;
		String recordLetto;
		String[] campiRecord;
		
		while (inizio<=fine) 
		{
			posizioneRicerca=(inizio+fine)/2;
			recordLetto=leggiFileRandom(nomeFile, posizioneRicerca);
			campiRecord=recordLetto.split(";");
			
			if (campiRecord[0].compareTo(nominativoDaCercare)==0)
				return posizioneRicerca;
			else
			{
				if (campiRecord[0].compareTo(nominativoDaCercare)<0)
				{
					inizio=posizioneRicerca+1;
				}
				else
					fine=posizioneRicerca-1;
			}		
		}
		return (-1);		
	}
	
	//Scrive un record (persona in formato stringa (CSV)) nella specificata posizione del file.
	//Se la posizione è <0 o >numero di record presenti+1, il record viene scritto in coda al file
	public static void scriviRecordInPosizione(String nomeFile, String record, int posizione) throws FileNotFoundException, IOException
	{
		RandomAccessFile raf= new RandomAccessFile(nomeFile, "rw");
		int recordPresenti;
		
		//controllo che il record sia lungo LUNGHEZZA_RECORD
		if (record.length()<LUNGHEZZA_RECORD)
		{
			for (int i = record.length(); i < LUNGHEZZA_RECORD; i++) 
			{
				record+=" ";
			}
		}
		else if (record.length()>LUNGHEZZA_RECORD)
			record=record.substring(0, LUNGHEZZA_RECORD-1);
		
		//controllo che la posizione non sia più grande del numero di file presenti, in tal caso scrivo in append in fondo al file
		recordPresenti=(int) (raf.length()/LUNGHEZZA_RECORD);
		if (posizione<1 || posizione>recordPresenti+1)
			posizione=recordPresenti+1;
		
		//scrivo il record nella posizione (l'eventuale record presente verrà sovrascritto)
		raf.seek((posizione-1)*LUNGHEZZA_RECORD);
		raf.writeBytes(record);
		raf.close();
	}
	
	//Elimina un record (persona in formato stringa (CSV)) in base al nominativo indicato
	private static String eliminaRecord (String nomeFile, String nominativoDaEliminare) throws FileNotFoundException, IOException
	{
		
		int posizioneDaEliminare;
		String recordLetto;
		int recordPresenti;
		
		RandomAccessFile raf=new RandomAccessFile(nomeFile, "rw");
		recordPresenti=(int) (raf.length()/LUNGHEZZA_RECORD);
		
		posizioneDaEliminare=ricercaBinariaPosizione(nomeFile, nominativoDaEliminare);
		if (posizioneDaEliminare>0)
		{
			for (int i = posizioneDaEliminare+1; i <=recordPresenti ; i++) 
			{
				recordLetto=leggiFileRandom(nomeFile, i);
				scriviRecordInPosizione(nomeFile, recordLetto, i-1);				
			}
			raf.setLength((recordPresenti-1)*LUNGHEZZA_RECORD);
			return "record eliminato correttamente";
		}
		else
			return "Record non presente";
		
	}
	
	/*
	private static String eliminaRecord(String nomeFile, String nominativoDaEliminare) throws IOException
	{
		RandomAccessFile raf= new RandomAccessFile(nomeFile, "rw");
		int posizioneDaEliminare=ricercaBinariaPosizione(nomeFile, nominativoDaEliminare);
		if (posizioneDaEliminare==-1)
			return ("Record non presente");
		String recordLetto;
		int numeroRecord=(int) (raf.length()/LUNGHEZZA_RECORD);
		
		long posizioneByteDaEliminare=(posizioneDaEliminare-1)*LUNGHEZZA_RECORD;
		
		for (int i = posizioneDaEliminare+1; i < numeroRecord; i++) 
		{
			recordLetto=leggiFileRandom(nomeFile, i);
			//System.out.println(recordLetto);
			raf.seek((i-2)*LUNGHEZZA_RECORD);
			raf.writeBytes(recordLetto);
		}
		
		raf.setLength((numeroRecord-1)*LUNGHEZZA_RECORD);
		raf.close();
		return ("record eliminato con successo");
		
	}
*/	
	
}
