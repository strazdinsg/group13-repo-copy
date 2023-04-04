package no.ntnu.idata2306.model;

import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;
import static org.springframework.security.crypto.bcrypt.BCrypt.hashpw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.idata2306.dto.SignUpDto;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * represent a user for the website.
 *
 * @author Edvin Astad
 * @version 17.03.2023
 */
@Schema(description = "Registered user on website", title = "User")
@Entity()
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private int id;
  @Column(name = "email", nullable = false, unique = true, updatable = false)
  private String email;
  @Column(name = "first_name", nullable = false, unique = false)
  private String firstName;
  @Column(name = "last_name", nullable = false, unique = false)
  private String lastName;
  @Column(name = "password", nullable = false, unique = false)
  private String password;
  @Column(name = "salt", nullable = false, unique = false, updatable = false)
  private String salt;
  @Column(name = "active", nullable = false, unique = false, updatable = true)
  private Boolean active;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
          joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
  )
  private Set<Role> roles = new LinkedHashSet<>();

  @OneToMany()
  private Set<Order> orders = new LinkedHashSet<>();

  /**
   * Constructor with parameters.
   *
   * @param email     users email.
   * @param firstName first and middle name(s) name of user.
   * @param lastName  surname of user.
   * @param password  users password.
   */
  public User(String email, String firstName, String lastName, String password) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salt = gensalt();
    this.password = hashpw(password, salt);
    this.active = true;
  }

  /**
   * Creates a new instance of User.
   *
   * @param userInfo information provided by SignUpDto instance
   */
  public User(SignUpDto userInfo) {
    this.email = userInfo.email();
    this.firstName = userInfo.firstName();
    this.lastName = userInfo.firstName();
    this.salt = gensalt();
    this.password = hashpw(password, salt);
    this.active = true;
  }

  /**
   * Empty constructor.
   */
  public User() {
  }

  /**
   * returns id.
   */
  public int getId() {
    return this.id;
  }

  /**
   * returns email.
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * returns first name.
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * returns surname.
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * returns hashed password.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * returns salt.
   */
  public String getSalt() {
    return this.salt;
  }

  /** returns active */
  public Boolean getActive() {
    return active;
  }

  /** returns roles */
  public Set<Role> getRoles() {
    return roles;
  }

  /** Returns orders */
  public Set<Order> getOrders() {
    return orders;
  }

  /**
   * Adds role.
   *
   * @param role role
   */
  public void addRole(Role role) {
    this.roles.add(role);
  }

  /**
   * Adds order.
   *
   * @param order order
   */
  public void addOrder(Order order) {
    this.orders.add(order);
  }

  /**
   * sets the value of id field to given value.
   *
   * @param id id of user
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * sets the value of email field to given value.
   *
   * @param email email of user
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * sets the value of firstName field to given value.
   *
   * @param firstName first name of user.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * sets the value of lastName field to given value.
   *
   * @param lastName surname of user.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * sets password to new password provided by user.
   *
   * @param password hash which will correspond with user password + salt.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * sets the value of the salt field to given value.
   * <b>Should never be used as salt should be final, but is required by spring</b>
   *
   * @param salt salt
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }

  /**
   * Sets the value of the active filed to given value.
   *
   * @param active active
   */
  public void setActive(Boolean active) {
    this.active = active;
  }

  /**
   * Sets the value of the roles filed to given value.
   *
   * @param roles roles
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * Sets the value of the orders filed to given value.
   *
   * @param orders
   */
  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }
}
