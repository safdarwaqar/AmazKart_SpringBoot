
package com.amazkart.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String phone;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Address> addresses = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Payment> payments = new ArrayList<>();
	
	@Override
	public int hashCode() {
	    int result = 17;
	    result = 31 * result + (id != null ? id.hashCode() : 0);
	    result = 31 * result + (username != null ? username.hashCode() : 0);
	    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
	    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
	    result = 31 * result + (phone != null ? phone.hashCode() : 0);
	    // Avoiding collections or other entities to prevent recursion
	    return result;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    
	    User user = (User) o;
	    return Objects.equals(id, user.id) &&
	           Objects.equals(username, user.username) &&
	           Objects.equals(firstName, user.firstName) &&
	           Objects.equals(lastName, user.lastName) &&
	           Objects.equals(phone, user.phone);
	}

}
