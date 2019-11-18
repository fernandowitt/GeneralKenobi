import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// Considerar que o número de generais falando a verdade deve ser 3f+1 para executar o algoritmo;
		boolean isGeneral = true;
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		ServerSocket serversocket;
		Socket socket;
		Socket conexao;
		PrintWriter pw;
		String decisao = null;
		int d = 0;
		String message = null;
		
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
				// Tenta enviar uma mensagem para cada contato?
				pw = new PrintWriter(socket.getOutputStream(), true);
				pw.println(decisao);
			}
		}else {
			// Receber uma mensagem do general comandante e verificar com os outros contatos?
			while(message == null) {
				serversocket = new ServerSocket(); // Qual porta eu uso e como decido qual porta?
				conexao = serversocket.accept();
				System.out.println("Nova conexão com o cliente " + conexao.getInetAddress().getHostAddress());
				
				try {
					Scanner scn = new Scanner(conexao.getInputStream());
					System.out.println(conexao);
					message = scn.nextLine();
					System.out.println(message);
				}catch(Exception e) {
					System.out.println("Não foi possível receber a mensagem");
				}
				//Será que esse while funciona? Não vejo como posso testar sozinho.
			}
			
			// A partir daqui eu verifico se a mensagem que eu recebi é a mesma das dos outros?
			// Criar uma lista de objetos Contato para cada general e fazer a verificação em cada um?
			// Se sim, como faço o algoritmo considerando a equação n >= 3f+1?
		}
		
		
	}

}
