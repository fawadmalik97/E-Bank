package myapp.ebank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Permissions {
	@Id
	@GeneratedValue
	private long id;
	private String permission;
	

}
