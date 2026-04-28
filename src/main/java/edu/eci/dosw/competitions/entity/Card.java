package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cards")
public class Card extends MatchEvent {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @Override
    public String getDescription() {
        return cardType + " card at minute " + getMinute();
    }

    // Getters y Setters
    public CardType getCardType() { return cardType; }
    public void setCardType(CardType cardType) { this.cardType = cardType; }
}