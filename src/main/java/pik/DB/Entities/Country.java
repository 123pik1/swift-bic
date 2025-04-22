package pik.DB.Entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.print.attribute.standard.MediaSize.ISO;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Country")
public class Country implements Storable{
	@Id
	@Getter
	@Setter
	@Column(name = "CountryISO2")
	String ISO2;

	@Getter
	@Setter
	@Column(name = "CountryName")
	String name;

	public Country (String countryISO2, String countryName)
	{
		this.ISO2 = countryISO2;
		this.name = countryName;
	}


	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(ISO2, country.getISO2()) && Objects.equals(name, country.getName());
	}

	@Override
    public int hashCode() {
        return Objects.hash(ISO2, name);
    }
}