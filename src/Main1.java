import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main1 {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// Considerar que o número de generais falando a verdade deve ser 3f+1 para executar o algoritmo;
		ArrayList<Contato> contatosNG = new ArrayList<Contato>();
		ServerSocket serversocket;
		ServerSocket askingsocket;
		Socket conexao;
		Socket asker;
		Socket answer;
		Socket s1, s2;
		PrintWriter askpw;
		String resposta = null;
		int attacc = 0;
		int protecc = 0;
		int total = 0;
		String message = null;
		
		Contato c2 = new Contato("localhost", 6782);
		Contato c3 = new Contato("localhost", 6783);
		
		contatosNG.add(c2);
//		contatosNG.add(c3);
		
		
			// Receber uma mensagem do general comandante e verificar com os outros contatos?
			while(message == null) {
				serversocket = new ServerSocket(6781); // Qual porta eu uso e como decido qual porta?
				conexao = serversocket.accept();
				System.out.println("Nova conexão com o cliente " + conexao.getInetAddress().getHostAddress());
				
				try {
					Scanner scn = new Scanner(conexao.getInputStream());
					message = scn.nextLine();
					System.out.println(message);
					Thread.currentThread().sleep(5000);
				}catch(Exception e) {
					System.out.println("Não foi possível receber a mensagem");
				}
				//Será que esse while funciona? Não vejo como posso testar sozinho. PS: Agora eu consegui :D mas deu pau :c
			}
			
			// A partir daqui eu verifico se a mensagem que eu recebi é a mesma das dos outros?
			// Criar uma lista de objetos Contato para cada general e fazer a verificação em cada um?
			
			for(Contato contato : contatosNG) {
				asker = new Socket(contato.getIp(), contato.getPorta());
				askpw = new PrintWriter(asker.getOutputStream(), true);
				askpw.println(message + "");
				
			}
			for(Contato contato : contatosNG) {
				askingsocket = new ServerSocket(contato.getPorta());
				answer = askingsocket.accept();
				System.out.println("Nova resposta do cliente " + answer.getInetAddress().getHostAddress());
				try {
					Scanner scn = new Scanner(answer.getInputStream());
					resposta = scn.nextLine();
					System.out.println(resposta);
					if(resposta=="Atacar") {
						attacc++;
					}else if(resposta=="Recuar") {
						protecc++;
					}
				}catch(Exception e) {
					System.out.println("Não foi possível receber a mensagem");
				}
			}
			System.out.println(attacc + ";" + protecc);
			// Se sim, como faço o algoritmo considerando a equação n >= 3f+1?
		
		
	}

}
