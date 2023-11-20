import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);

        menu(leitor);

        //SCRIPT PARA RODAR A APLICAÇÃO
        //java -classpath ".;../sqlite-jdbc-3.43.0.0.jar" Connect
    }

    public static void menu(Scanner leitor) {
        int entrada;
        do{

            System.out.println("\n____________ MENU ____________\n");

            System.out.println("1- Criar um novo usuario");
            System.out.println("2- Enviar uma nova mensagem");
            System.out.println("3- Ler todas as mensagens");
            System.out.println("4- Ler mensagens enviadas de um usuario");
            System.out.println("5- Ler mensagens recebidas de um usuario");
            System.out.println("6- Ler todos os usuarios");
            System.out.println("0- Sair");
            entrada = leitor.nextInt();
            

            switch (entrada) {
                case 1:
                    novoUsuario(leitor);
                    break;
                case 2:
                    novaMensagem(leitor);
                    break;
                case 3:
                    lerTodasMensagens();
                    break;
                case 4:
                    // lerMensagensRemetente(leitor);
                    break;
                case 5:
                    // lerMensagensDestinatario(leitor);
                    break;
                case 6:
                    lerUsuarios();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Digite uma opcao correta!");
                    break;
            }
        }while(entrada != 0);
    }

    public static void novoUsuario(Scanner leitor) {
        leitor.nextLine();
        System.out.println("Digite o apelido do usuario:");
        
        String apelido = leitor.nextLine();

        new Usuario().novoUsuario(apelido);
    }
    
    public static void novaMensagem(Scanner leitor) {
        System.out.println("Digite o remetente:");
        leitor.nextLine();
        String remetente = leitor.nextLine();
        
        System.out.println("Digite o destinatario:");
        String destinatario = leitor.nextLine();

        System.out.println("Digite o texto:");
        String texto = leitor.nextLine();
        
        new Mensagem().novaMensagem(remetente, destinatario, texto);
    }
    
    public static void lerTodasMensagens() {
        new Mensagem().lerMensagem();
    }
    
    public static void lerUsuarios() {
        new Usuario().lerUsuario();
    }
}