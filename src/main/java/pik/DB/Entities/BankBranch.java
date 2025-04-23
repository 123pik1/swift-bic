package pik.DB.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pik.Exceptions.WrongSwiftCodeException;
import pik.Server.Server;

@Entity
@Table(name = "Branch")
public class BankBranch implements Storable{

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
	@ManyToOne
	Country country;

	@Getter
	@Setter
	@Column(name = "isHeadquarter")
	boolean isHeadquarter;

	@Id
	@Getter
	@Setter
	@Column(name = "Swiftcode")
	String swiftCode;

	@Transient
	List<BankBranch> branches;

	public BankBranch(String address, String bankName, Country country, String swiftCode) {
		this.address = address;
		this.bankName = bankName;
		this.country = country;
		this.swiftCode = swiftCode;
		this.isHeadquarter = isHeadquarter(swiftCode);
		if (this.isHeadquarter)
			setBranches();
	}

	private void setBranches()
	{
		branches = Server.getDbHandler().queries.getSubBranches(swiftCode.substring(0, 8));
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

	@Override
	public String toString() {
		return "BankBranch [address=" + address + ", bankName=" + bankName + ", countryISO2=" + country.getISO2()
				+ ", countryName=" + country.getName() + ", isHeadquarter=" + isHeadquarter + ", swiftCode=" + swiftCode
				+ ", branches=" + branches + "]";
	}

	public String toJsonString(boolean asBranch, int depth) {
		/*
		 * false when it is root
		 * true when subBranch
		 */
        String jsonString = "{";
        jsonString += "\"address\": \"" + this.address + "\",";
        jsonString += "\"bankName\": \"" + this.bankName + "\",";
        jsonString += "\"countryISO2\": \"" + this.country.getISO2() + "\",";
		if (!asBranch)
        	jsonString += "\"countryName\": \"" + this.country.getName() + "\",";
        jsonString += "\"isHeadquarter\": " + this.isHeadquarter + ",";
        jsonString += "\"swiftCode\": \"" + this.swiftCode + "\"";
		if (this.isHeadquarter && !asBranch && depth>0)
		{
			setBranches();
			jsonString+=",";
			jsonString+="\"branches\" : [";
			boolean firstIter = true;
			for (BankBranch branch : branches)
			{
				if (!firstIter)
				{
					jsonString+=",";
					firstIter=!firstIter;
				}
				jsonString+=branch.toJsonString(true, 0);
			}
			jsonString+="]";
		}
        jsonString += "}";
        return jsonString;
    }
	public String toJsonString(boolean asBranch) {
		return toJsonString(asBranch, 1);
	}
}
