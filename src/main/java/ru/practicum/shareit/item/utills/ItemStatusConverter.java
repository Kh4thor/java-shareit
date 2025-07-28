package ru.practicum.shareit.item.utills;

public class ItemStatusConverter {

	public ItemStatus convertStringToItemStatus(String itemStatusString) throws Exception {
		itemStatusString.toUpperCase();
		switch (itemStatusString) {
		case "AVALIABLE":
			return ItemStatus.AVAILABLE;
		case "NOT_AVALIABLE":
			return ItemStatus.NOT_AVAILABLE;
		default:
			throw new IllegalArgumentException(
					"Значение полученной строки:" + itemStatusString
							+ " не соовтетвтует значениям перечисления ItemStatus");
		}
	}
}
