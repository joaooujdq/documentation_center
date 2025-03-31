package com.example.documentation_center;
import com.example.documentation_center.models.Branch;
import com.example.documentation_center.models.Folder;
import com.example.documentation_center.models.Card;
import com.example.documentation_center.models.User;
import com.example.documentation_center.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import java.util.Arrays;
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class BackendTsApplication implements CommandLineRunner {


	@Autowired
	private UserDAO userDAO;
	@Autowired
	private BranchDAO branchDAO;
	@Autowired
	private FolderDAO folderDAO;
	@Autowired
	private CardDAO cardDAO;

	public static void main(String[] args) {
		SpringApplication.run(BackendTsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		User u1 = new User(1,  "true", "nome1", "descricao1", "senha1");
		User u2 = new User(2,  "false", "nome2", "descricao2", "senha2");
		User u3 = new User(3,  "false", "nome3", "descricao3", "senha3");
		User u4 = new User(4,  "false", "nome4", "descricao4", "senha4");
		User u5 = new User(5,  "false", "nome5", "descricao5", "senha5");
		Branch b1 = new Branch(1, "descricao1", "nome1", u1);
		Branch b2 = new Branch(2, "descricao2", "nome2", u2);
		Branch b3 = new Branch(3, "descricao3", "nome3",  u3);
		Branch b4 = new Branch(4, "descricao4", "nome4",  u4);
		Branch b5 = new Branch(5, "descricao5", "nome5",  u5);
		Folder f1 = new Folder(1, "descricao1", "nome1", b1);
		Folder f2 = new Folder(2, "descricao2", "nome2", b2);
		Folder f3 = new Folder(3, "descricao3", "nome3",  b3);
		Folder f4 = new Folder(4, "descricao4", "nome4",  b4);
		Folder f5 = new Folder(5, "descricao5", "nome5",  b5);
		Card c1 = new Card(1, "thumbnail1", "imageLink1", "descricao1", "nome1", f1 );
		Card c2 = new Card(2, "thumbnail2", "imageLink2", "descricao2", "nome2", f2);
		Card c3 = new Card(3, "thumbnail3", "imageLink3", "descricao3", "nome3", f3);
		Card c4 = new Card(4, "thumbnail4", "imageLink4", "descricao4", "nome4", f4);
		Card c5 = new Card(5, "thumbnail5", "imageLink5", "descricao5", "nome5", f5);

		userDAO.saveAll(Arrays.asList(u1,u2,u3,u4,u5));
		branchDAO.saveAll(Arrays.asList(b1,b2,b3,b4,b5));
		folderDAO.saveAll(Arrays.asList(f1,f2,f3,f4,f5));
		cardDAO.saveAll(Arrays.asList(c1,c2,c3,c4,c5));
	}
}
