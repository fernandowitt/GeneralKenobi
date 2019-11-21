import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main3 {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// Considerar que o número de generais falando a verdade deve ser 3f+1 para executar o algoritmo;
		boolean isGeneral = false;
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		ArrayList<Contato> contatosNG = new ArrayList<Contato>();
		ServerSocket serversocket;
		ServerSocket askingsocket;
		Socket socket;
		Socket conexao;
		Socket asker;
		Socket answer;
		PrintWriter pw;
		PrintWriter askpw;
		String decisao = null;
		String resposta = null;
		int attacc = 0;
		int protecc = 0;
		int total = 0;
		int d = 0;
		String message = null;
		
		
		Contato c1 = new Contato("localhost", 6781);
		Contato c2 = new Contato("localhost", 6782);
		Contato c3 = new Contato("localhost", 6783);
		contatos.add(c1);
		contatos.add(c2);
		contatos.add(c3);
		
		contatosNG.add(c1);
		contatosNG.add(c2);
		
		if(isGeneral) {
			while (d!=1 && d!=2) {
				decisao = JOptionPane.showInputDialog("Atacar = 1\nRecuar = 2");
				d = Integer.parseInt(decisao);
				if (d!=1 && d!=2) {
					JOptionPane.showMessageDialog(null, "Você não respondeu a pergunta.");
				}else {
					if(d==1)
						decisao="Atacar";
					else if (d==2)
						decisao="Recuar";
					System.out.println(decisao);
				}
			}
			for(Contato contato: contatos) {
				socket = new Socket(contato.getIp(), contato.getPorta());
				pw = new PrintWriter(socket.getOutputStream(), true);
				pw.println(decisao);
			}
		}else {
			// Receber uma mensagem do general comandante e verificar com os outros contatos?
			while(message == null) {
				serversocket = new ServerSocket(6783); // Qual porta eu uso e como decido qual porta?
				conexao = serversocket.accept();
				System.out.println("Nova conexão com o cliente " + conexao.getInetAddress().getHostAddress());
				
				try {
					Scanner scn = new Scanner(conexao.getInputStream());
					message = scn.nextLine();
					System.out.println(message);
				}catch(Exception e) {
					System.out.println("Não foi possível receber a mensagem");
				}
				//Será que esse while funciona? Não vejo como posso testar sozinho.
			}
			
			// A partir daqui eu verifico se a mensagem que eu recebi é a mesma das dos outros?
			// Criar uma lista de objetos Contato para cada general e fazer a verificação em cada um?
			for(Contato contato : contatosNG) {
				asker = new Socket(contato.getIp(), contato.getPorta());
				askpw = new PrintWriter(asker.getOutputStream(), true);
				askpw.println(message);
				
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

}
