package Model

import org.specs2.mock.Mockito


trait ModelMocks extends Mockito {

    def mockContact(name: String, lastName: String, phone: Set[String]) = {
    val contact = mock[Contact]
    contact.name returns name
    contact.lastName returns lastName
    contact.phone returns phone
    contact
  }


  }