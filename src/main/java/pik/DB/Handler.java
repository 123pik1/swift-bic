package pik.DB;

import org.hibernate.SessionFactory;

import lombok.Getter;

public class Handler {
	@Getter
	private SessionFactory factory;

	public Handler()
	{
		
	}

}
