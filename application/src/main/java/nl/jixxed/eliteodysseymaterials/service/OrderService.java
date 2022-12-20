package nl.jixxed.eliteodysseymaterials.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.jixxed.eliteodysseymaterials.domain.BuyOrder;
import nl.jixxed.eliteodysseymaterials.domain.SellOrder;
import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.service.event.EventListener;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.JournalInitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderService {
    private static final Map<Material, BuyOrder> BUY_ORDERS = new HashMap<>();
    private static final Map<Material, SellOrder> SELL_ORDERS = new HashMap<>();
    private static final List<EventListener<?>> EVENT_LISTENERS = new ArrayList<>();

    public static void clearOrders() {
        BUY_ORDERS.clear();
        SELL_ORDERS.clear();
    }

    static {
        EVENT_LISTENERS.add(EventService.addStaticListener(JournalInitEvent.class, event -> {
            if (event.isInitialised()) {
                clearOrders();
            }
        }));
    }

    public static void addBuyOrder(final Material material, final Integer price, final Integer total,
                                   final Integer outstanding) {
        BUY_ORDERS.put(material, new BuyOrder(price, total, outstanding));
    }

    public static void addSellOrder(final Material material, final Integer price, final Integer stock) {
        SELL_ORDERS.put(material, new SellOrder(price, stock));
    }

    static BuyOrder getBuyOrder(final Material material) {
        return BUY_ORDERS.get(material);
    }

    static SellOrder getSellOrder(final Material material) {
        return SELL_ORDERS.get(material);
    }

}
