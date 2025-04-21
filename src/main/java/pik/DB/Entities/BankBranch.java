package pik.DB.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.antlr.v4.parse.ANTLRParser.throwsSpec_return;

import lombok.Getter;
import lombok.Setter;
import pik.Exceptions.WrongSwiftCodeException;

@Entity
@Table(name = "Branch")
public class BankBranch {

	@Getter
	@Setter
	@Column(name = "Address")
	String address;

	@Getter
	@Setter
	@Column(name = "BankName")
	String bankName;

	@Getter
	@Setter
	@Column(name = "CountryISO2")
	String countryISO2;

	@Getter
	@Setter
	@Column(name = "CountryName")
	String countryName;

	@Getter
	@Setter
	@Column(name = "isHeadquarter")
	boolean isHeadquarter;

	@Id
	@Getter
	@Setter
	@Column(name = "Swift-code")
	String swiftCode;

	@Transient
	List<BankBranch> branches;

	public BankBranch(String address, String bankName, String countryISO2, String countryName, String swiftCode) {
		this.address = address;
		this.bankName = bankName;
		this.countryISO2 = countryISO2;
		this.countryName = countryName;
		this.swiftCode = swiftCode;
		this.isHeadquarter = isHeadquarter(swiftCode);

	}

	private boolean isHeadquarter(String swiftCode) {
		if (swiftCode.length() >= 3) {
			String last3Chars = swiftCode.substring(swiftCode.length() - 3);
			if (last3Chars.equals("XXX"))
				return true;
			else
				return false;
		} else
			throw new WrongSwiftCodeException();
	}

}
