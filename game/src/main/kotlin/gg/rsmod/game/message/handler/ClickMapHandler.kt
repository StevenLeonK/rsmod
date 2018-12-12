package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.ClickMapMovementMessage
import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Client

/**
 * @author Tom <rspsmods@gmail.com>
 */
class ClickMapHandler : MessageHandler<ClickMapMovementMessage> {

    override fun handle(client: Client, message: ClickMapMovementMessage) {
        log(client, "Click Map: x={}, z={}, type={}", message.x, message.z, message.movementType)

        client.teleport(Tile(message.x, message.z))
        client.interruptPlugins()

        val directions = Direction.values().filter { it != Direction.NONE }
        println("Can traverse:")
        directions.forEach { dir ->
            val dirTile = client.tile.step(1, dir)
            val chunk = client.world.collision.regions.getChunkForTile(client.world, dirTile)
            val matrix = chunk.getMatrix(client.tile.height)
            val localX = dirTile.x % 8
            val localZ = dirTile.z % 8
            println("\t$dir: ${client.world.collision.canTraverse(client.world, client.tile, client.getType(), dir)}")
        }
    }
}