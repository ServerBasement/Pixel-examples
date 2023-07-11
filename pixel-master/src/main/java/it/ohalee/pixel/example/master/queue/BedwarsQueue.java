package it.ohalee.pixel.example.master.queue;

import it.ohalee.pixel.api.MapSupplier;
import it.ohalee.pixel.api.QueueStealer;
import it.ohalee.pixel.example.master.player.BedwarsParticipator;
import it.ohalee.pixel.example.match.SharedBedwarsMatch;
import it.ohalee.pixel.example.match.enumeration.BedwarsType;
import it.ohalee.pixel.match.SharedMatchStatus;
import it.ohalee.pixel.queue.QueueStatus;
import it.ohalee.pixel.queue.StandardQueue;
import it.ohalee.pixel.util.Basement;
import it.ohalee.pixel.util.StaticTask;

import java.util.UUID;

public class BedwarsQueue extends StandardQueue<BedwarsType, SharedBedwarsMatch, BedwarsParticipator> {

    public BedwarsQueue(String mode, BedwarsType queueType, MapSupplier supplier) {
        super(mode, queueType, supplier);
    }

    @Override
    public SharedBedwarsMatch seekMatch(int weight) {
        if (tunnels.isEmpty()) return createMatch();
        SharedBedwarsMatch best = null;
        for (SharedBedwarsMatch match : tunnels.values()) {
            if (match.getStatus() != SharedMatchStatus.OPEN || !match.canCarry(weight)) continue;

            best = match;
        }
        return best == null ? createMatch() : best;
    }

    @Override
    public SharedBedwarsMatch summonMatch() {
        return new SharedBedwarsMatch(UUID.randomUUID().toString().substring(0, 4) + mode + UUID.randomUUID().toString().substring(0, 3), queueType);
    }

    @Override
    public void task() {
        status = QueueStatus.TASK;
        StaticTask.runBukkitTaskTimer(summonStealer(), 2L, 2L, true);
    }

    @Override
    public QueueStealer<BedwarsType, SharedBedwarsMatch, BedwarsParticipator, BedwarsQueue> summonStealer() {
        return new BedwarsQueueStealer(
                Basement.rclient().getLock("lock_" + queueType.name()),
                this
        );
    }

}
