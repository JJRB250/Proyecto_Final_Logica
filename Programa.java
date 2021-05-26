import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
import java.util.*;

public class Programa{

	public static String convertirUnicode(String letra,String cadena){
		
		StringBuilder str = new StringBuilder(cadena);
		int indice = -1;
		char caracter = 0;
		indice = str.lastIndexOf(letra);
		
		if(indice>=0 )
		{
			switch(letra){
				case "Á": caracter = '\u00C1';
						  break;
				case "á": caracter = '\u00E1';
					      break;
				case "é": caracter = '\u00E9';
					      break;
				case "í": caracter = '\u00ED';
					      break;
		        case "ó": caracter = '\u00F3';
					      break;
			    case "ú": caracter = '\u00FA';
					      break;
				case "ñ": caracter = '\u00F1';
					      break;
			}
			// System.out.println("\\u" + Integer.toHexString('÷' | 0x10000).substring(1));
			str.replace(indice,indice+1,""+caracter);
		}

		return str.toString();
	}

	public static void imprimir(String cadena)
	{
		String str; 
		str = convertirUnicode("Á",cadena);
		str = convertirUnicode("á",str);
		str = convertirUnicode("é",str);
		str = convertirUnicode("í",str);
		str = convertirUnicode("ó",str);
		str = convertirUnicode("ú",str);
		str = convertirUnicode("ñ",str);

		System.out.println(str);
	}

	public static StringBuilder obtenerLetraCancion(int inicio,int fin, String[]data)
	{
		StringBuilder str = new StringBuilder();
		StringTokenizer temp;

		for(int i = inicio; i<=fin; i++)
		{
			//System.out.println("@  "+data[i]);

			temp = new StringTokenizer(data[i],";");

			while(temp.hasMoreTokens())
			{
				System.out.print(temp.nextToken()+" ");
			}
			System.out.println();
			//str.append(data[i]+"\n");
		}

		return str;
	}

	public static void menu(){
System.out.println("  _______________________________________________________________________________");
System.out.println(" |                                                                               |");
System.out.println(" | '||    ||'                  ||             |'''''||                           |"); 
System.out.println(" |  |||  |||  ... ...   ....  ...    ....         .|'     ...   .. ...     ....  |"); 
System.out.println(" |  |'|..'||   ||  ||  ||. '   ||  .|   ''       ||     .|  '|.  ||  ||  .|...|| |"); 
System.out.println(" |  | '|' ||   ||  ||  . '|..  ||  ||          .|'      ||   ||  ||  ||  ||      |"); 
System.out.println(" |. |. | .||.  '|..'|. |'..|' .||.  '|...'    ||......|  '|..|' .||. ||.  '|...' |"); 
System.out.println(" |_______________________________________________________________________________|");
imprimir(" |                                                          	                 |");
imprimir(" |                    Ingrese una opción así:		 	                 |");
imprimir(" |                    1. Que canciones tenemos?			 	         |");
imprimir(" |                    2. Comenzar con el karaoke			         |");
imprimir(" |                    3. Detener karaoke				         |");
imprimir(" |                    4. Salir					                 |");
imprimir(" |_______________________________________________________________________________|");
imprimir("¿Que opcion desea? = ");
			}

	public static void main(String[] args) {
		
		//AnsiConsole.systemInstall();
		
		Audio audio = new Audio();
		int centinela = 0;	
		int indice_cancion = 0;
		int inicio_letra = 0, fin_letra = 0;
		String [] canciones;
		String [][] info_canciones;
		StringBuilder letra_cancion;

		canciones = ConsoleFile.readBigFile("recursos/letras.csv");
		info_canciones = ConsoleData.dataList(canciones);

		try{
			
			do{

				// imprimir(""+RandomHelper.random(1,10));

				System.out.println();
				//TODO: Terminar la funcion para que imprima todos los caracteres especiales que use el programa
				menu();
				//TODO: Ojo falta validar la entrada de datos
				//TODO: Recuerde usar el helper ConsoleInput y validar
				centinela = ConsoleInput.getInt();

				if(centinela== 1)
					{
						/* La informacion de las canciones esta
						en la matriz info_canciones, acá un ejemplo de como imprimir
						el nombre de la primer canción y su autor */
					
						//TODO: Ojo, falta validar el valor ingresado
						imprimir("Ingrese indice de la cancion, entre 0 y "+(info_canciones.length-1));
						indice_cancion = ConsoleInput.getInt();

						inicio_letra = ConsoleInput.stringToInt(info_canciones[indice_cancion][ConsoleData.INICIO_CANCION]);
						fin_letra = ConsoleInput.stringToInt(info_canciones[indice_cancion][ConsoleData.FIN_CANCION]);

						System.out.println();
						imprimir("Nombre "+info_canciones[indice_cancion][ConsoleData.NOMBRE_CANCION]);
						imprimir("Autor "+info_canciones[indice_cancion][ConsoleData.AUTOR_CANCION]);
						imprimir("Archivo "+info_canciones[indice_cancion][ConsoleData.RUTA_CANCION]);
					}

				if(centinela == 2)
					{
						//TODO: Controlar que el archivo de la cancion exista
						imprimir("Ingrese indice de la cancion, entre 0 y "+(info_canciones.length-1));
						indice_cancion = ConsoleInput.getInt();
						audio.seleccionarCancion(info_canciones[indice_cancion][ConsoleData.RUTA_CANCION]);
						audio.reproducir();
							
			
						inicio_letra = ConsoleInput.stringToInt(info_canciones[indice_cancion][ConsoleData.INICIO_CANCION]);
						fin_letra = ConsoleInput.stringToInt(info_canciones[indice_cancion][ConsoleData.FIN_CANCION]);						
						letra_cancion = obtenerLetraCancion(inicio_letra,fin_letra,canciones);
						imprimir(letra_cancion.toString());
					}	
				if(centinela == 3)
					{
						audio.detener();
					}

			}while(centinela!=4);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally{
			audio.detener();
		}
	}
}