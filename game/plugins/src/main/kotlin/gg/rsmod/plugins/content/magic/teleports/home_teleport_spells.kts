package gg.rsmod.plugins.content.magic.teleports

import gg.rsmod.plugins.content.magic.MagicSpells.on_magic_spell_button

val TERMINATE_HOME_TELEPORT_NEUTRAL: QueueTask.() -> Unit = {
    player.animate(-1)
    player.graphic(-1)
}

val TERMINATE_HOME_TELEPORT_SITTING: QueueTask.() -> Unit = {
    player.animate(4852)
    player.graphic(-1)
}

HomeTeleport.values.forEach { teleport ->
    on_magic_spell_button(teleport.spellName) {
        if (player.hasMoveDestination()) {
            player.message("You can't use that teleport at the moment.")
            return@on_magic_spell_button
        }
        player.queue(TaskPriority.STRONG) {
            teleport(teleport.endTile(world))
        }
    }
}

suspend fun QueueTask.teleport(endTile: Tile) {
    wait(2)
    terminateAction = TERMINATE_HOME_TELEPORT_NEUTRAL
    player.animate(4847)
    player.graphic(800)
    player.playSound(193)
    wait(7)
    terminateAction = TERMINATE_HOME_TELEPORT_SITTING
    player.animate(4850)
    player.playSound(196)
    wait(6)
    player.animate(4853)
    player.graphic(802, 0)
    player.playSound(194)
    wait(4)
    player.animate(4855)
    player.graphic(803, 0)
    player.playSound(195)
    wait(3)
    player.graphic(804)
    wait(1)
    player.animate(4857)
    wait(2)
    player.animate(-1)
    player.teleport(endTile)
}

enum class HomeTeleport(val spellName: String, val endTile: World.() -> Tile) {
    LUMBRIDGE("Lumbridge Home Teleport", { gameContext.home }),
    EDGEVILLE("Edgeville Home Teleport", { gameContext.home }),
    LUNAR("Lunar Home Teleport", { gameContext.home }),
    ARCEUUS("Arceuus Home Teleport", { gameContext.home })
    ;

    companion object {
        val values = enumValues<HomeTeleport>()
    }
}