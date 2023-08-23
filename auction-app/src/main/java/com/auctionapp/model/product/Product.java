package com.auctionapp.model.product;

import com.auctionapp.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = "^(?!.*  )(?!\\s)(?!.*\\s$)[\\p{Alpha} ]*$", message = "Invalid name format. " +
            "Name should contain only alphabets and space. Not start, end with space and should not contain consecutive spaces.  ")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private EProductCategory category;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Basic
    @Column(name = "url")
    @NotBlank(message = "URL cannot be blank")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Invalid URL format")
    private String url;

}

