package Entity;

import java.util.Date;

public class ReaderCard extends Reader {
    private String cardNumber;
    private Date dateOfCard;

    public ReaderCard(String CardNumber, Date dateOfCard, Reader reader) {
        super();
        this.setId(reader.getId());
        this.setEmail(reader.getEmail());
        this.setPhoneNumber(reader.getPhoneNumber());
        this.setAddressId(reader.getAddressId());
        this.setFullname(reader.getFullname());
        this.setDateOfBirth(reader.getDateOfBirth());
        this.cardNumber = CardNumber;
        this.dateOfCard = dateOfCard;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getDateOfCard() {
        return dateOfCard;
    }

    public void setDateOfCard(Date dateOfCard) {
        this.dateOfCard = dateOfCard;
    }

}
