package application.chatbot;

public enum ChatBotIntents {
	appointmentsToday("appointments_today"), appointmentTimeleft("appointment_timeleft"), appointmentNext("appointment_next"), searchCustomer("search_customer"), appoinmentNextLocation("appointment_next_location"), appointmentCreateToday("appointment_create_today"), defaultIntent("");

	private String valueAttr;

	private ChatBotIntents(String value) {
		this.valueAttr = value;
	}

	public String getString() {
		return this.valueAttr;
	}
}
