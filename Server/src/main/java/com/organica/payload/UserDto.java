package com.organica.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.organica.entities.Cart;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @JsonProperty("userid")
    private int Userid;
    @JsonProperty("name")
    @Column(name = "username")
    private String Name;
    @JsonProperty("email")
    private String Email;
    @JsonProperty("password")
    private String Password;
    @JsonProperty("contact")
    private String Contact;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("role")
    private String Role;
}
