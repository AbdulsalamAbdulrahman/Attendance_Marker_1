package tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentTableAdapter {

        /*    */   private final SimpleIntegerProperty id;
        /*    */   private final SimpleStringProperty firstName;
        /*    */   private final SimpleStringProperty lastName;
        /*    */   private final SimpleStringProperty otherName;
        /*    */   private final SimpleStringProperty sex;
        /*    */   private final SimpleStringProperty regNumber;
        /*    */
/*    */   public StudentTableAdapter(SimpleIntegerProperty id, SimpleStringProperty firstName, SimpleStringProperty lastName,
                                      SimpleStringProperty otherName, SimpleStringProperty sex, SimpleStringProperty regNumber)
/*    */   {
/* 17 */     this.id = id;
/* 18 */     this.firstName = firstName;
/* 19 */     this.lastName = lastName;
/* 20 */     this.otherName = otherName;
/* 21 */     this.sex = sex;
/* 22 */     this.regNumber = regNumber;
/*    */   }
        /*    */
/*    */   public String getLastName()
/*    */   {
/* 27 */     return this.lastName.get();
/*    */   }
        /*    */
/*    */   public SimpleStringProperty lastNameProperty() {
/* 31 */     return this.lastName;
/*    */   }
        /*    */
/*    */   public void setLastName(String lastName) {
/* 35 */     this.lastName.set(lastName);
/*    */   }
        /*    */
/*    */   public String getOtherName() {
/* 39 */     return this.otherName.get();
/*    */   }
        /*    */
/*    */   public SimpleStringProperty otherNameProperty() {
/* 43 */     return this.otherName;
/*    */   }
        /*    */
/*    */   public void setOtherName(String otherName) {
/* 47 */     this.otherName.set(otherName);
/*    */   }
        /*    */
/*    */   public String getSex() {
/* 51 */     return this.sex.get();
/*    */   }
        /*    */
/*    */   public SimpleStringProperty sexProperty() {
/* 55 */     return this.sex;
/*    */   }
        /*    */
/*    */   public void setSex(String sex) {
/* 59 */     this.sex.set(sex);
/*    */   }
        /*    */
/*    */   public int getId() {
/* 63 */     return this.id.get();
/*    */   }
        /*    */
/*    */   public SimpleIntegerProperty idProperty() {
/* 67 */     return this.id;
/*    */   }
        /*    */
/*    */   public void setId(int id) {
/* 71 */     this.id.set(id);
/*    */   }
        /*    */
/*    */   public String getFirstName() {
/* 75 */     return this.firstName.get();
/*    */   }
        /*    */
/*    */   public SimpleStringProperty firstNameProperty() {
/* 79 */     return this.firstName;
/*    */   }
        /*    */
/*    */   public void setFirstName(String firstName) {
/* 83 */     this.firstName.set(firstName);
/*    */   }
        /*    */
/*    */   public String getRegNumber() {
/* 87 */     return this.regNumber.get();
/*    */   }
        /*    */
/*    */   public SimpleStringProperty regNumberProperty() {
/* 91 */     return this.regNumber;
/*    */   }
        /*    */
/*    */   public void setRegNumber(String regNumber) {
/* 95 */     this.regNumber.set(regNumber);
/*    */   }
/*    */ }

