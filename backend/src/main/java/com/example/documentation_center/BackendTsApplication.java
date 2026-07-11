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
import org.springframework.core.env.Environment;
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
	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(BackendTsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		if (Arrays.asList(environment.getActiveProfiles()).contains("h2")) {
			return;
		}

		User u1 = new User(1L, "nome1" , "desc1", "senha1", true, true, true, true, true, 1L);
		User u2 = new User(2L, "nome2" , "desc2", "senha2", true, true, true, true, false, 2L);
		User u3 = new User(3L, "nome3" , "desc3", "senha3", true, true, true, true, false, 3L);
		User u4 = new User(4L, "nome4" , "desc4", "senha4", true, true, true, true, false, 3L);
		User u5 = new User(5L, "nome5" , "desc5", "senha5", true, true, true, true, false, 4L);

		Branch b1 = new Branch(1L,  "nome1", "desc1");
		Branch b2 = new Branch(2L,  "nome2", "desc2");
		Branch b3 = new Branch(3L,  "nome3", "desc3");
		Branch b4 = new Branch(4L,  "nome4", "desc4");
		Branch b5 = new Branch(5L,  "nome5", "desc5");

		Folder f1 = new Folder(1L,  1L, 1L, "nome1", "desc1");
		Folder f2 = new Folder(2L,  3L, 2L, "nome2", "desc2");
		Folder f3 = new Folder(3L,  3L, 3L, "nome3", "desc3");
		Folder f4 = new Folder(4L,  2L, 4L, "nome4", "desc4");
		Folder f5 = new Folder(5L,  5L, 4L, "nome5", "desc5");

		Card c1 = new Card(1L,  "nome1","desc1" , "image1","thumb1" ,1L,1L,1L);
		Card c2 = new Card(2L,  "nome2","desc2" , "image2","thumb2" ,2L,2L,2L);
		Card c3 = new Card(3L,  "nome3", "desc3", "image3", "thumb3",3L,3L,3L);
		Card c4 = new Card(4L,  "nome4","desc4" , "image4", "thumb4",4L,4L,4L);
		Card c5 = new Card(5L,  "nome5", "desc5", "image5","thumb5" ,5L,5L,5L);

		userDAO.saveAll(Arrays.asList(u1,u2,u3,u4,u5));
		branchDAO.saveAll(Arrays.asList(b1,b2,b3,b4,b5));
		folderDAO.saveAll(Arrays.asList(f1,f2,f3,f4,f5));
		cardDAO.saveAll(Arrays.asList(c1,c2,c3,c4,c5));


	}
}
