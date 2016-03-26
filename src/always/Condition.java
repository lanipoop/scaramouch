package always;

public enum Condition {
     NWT("new with tags"),
     NWOT("new without tags"),
     Preowned("preowned"),
     Great("in great condition"),
     Good("in good condition");
     
     private final String description;
     
     private Condition(final String description) {
         this.description = description;
     }

     public String getDescription() {
         return description;
     }
}
