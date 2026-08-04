package com.skd.ascendantspawners.compat;

import com.skd.ascendantspawners.AscendantSpawners;
import com.skd.ascendantspawners.modifiers.SpawnerModifier;
import com.skd.ascendantspawners.modifiers.StatModifier;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.joml.Matrix3x2fStack;

public class SpawnerCategory implements IRecipeCategory<SpawnerModifier> {
    public static final Identifier TEXTURES = AscendantSpawners.loc("textures/gui/spawner_jei.png");
    public static final Identifier UID = AscendantSpawners.loc("spawner_modifiers");
    public static final IRecipeType<SpawnerModifier> TYPE = IRecipeType.create(AscendantSpawners.loc("spawner_modifiers"), SpawnerModifier.class);
    private IDrawable bg;
    private IDrawable icon;
    private Component title;

    public SpawnerCategory(IGuiHelper helper) {
        this.bg = helper.drawableBuilder(TEXTURES, 0, 0, 169, 75).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.SPAWNER));
        this.title = AscendantSpawners.lang("title", "spawner");
    }

    @Override
    public IRecipeType<SpawnerModifier> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public int getWidth() {
        return 169;
    }

    @Override
    public int getHeight() {
        return 75;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpawnerModifier recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 11, 11).add(recipe.mainHand());
        if (recipe.offHand().isPresent()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 11, 48).add(recipe.offHand().get());
        }

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, -1000, -1000).add(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.SPAWNER));
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(SpawnerModifier recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor gfx, double mouseX, double mouseY) {
        this.bg.draw(gfx, 0, 0);
        if (recipe.offHand().isEmpty()) {
            gfx.blit(RenderPipelines.GUI_TEXTURED, TEXTURES, 1, 31, 0.0F, 88.0F, 28, 34, 256, 256);
        }

        Font font = Minecraft.getInstance().font;
        if (mouseX >= -1.0 && mouseX < 9.0 && mouseY >= 13.0 && mouseY < 25.0) {
            gfx.blit(RenderPipelines.GUI_TEXTURED, TEXTURES, -1, 13, 0.0F, 75.0F, 10, 12, 256, 256);
        }
        else if (mouseX >= -1.0 && mouseX < 9.0 && mouseY >= 50.0 && mouseY < 62.0 && recipe.offHand().isPresent()) {
            gfx.blit(RenderPipelines.GUI_TEXTURED, TEXTURES, -1, 50, 0.0F, 75.0F, 10, 12, 256, 256);
        }

        Matrix3x2fStack mvStack = gfx.pose();
        mvStack.pushMatrix();
        mvStack.translate(0.0F, 0.5F);
        gfx.fakeItem(new ItemStack(Items.SPAWNER), 31, 29);
        mvStack.popMatrix();
        int top = 37 - recipe.statModifiers().size() * (9 + 2) / 2 + 2;
        int left = 168;

        for (StatModifier<?> s : recipe.statModifiers()) {
            String value = s.getFormattedValue();

            Component msg = switch (s.mode()) {
                case ADD -> {
                    if ("true".equals(value)) {
                        value = "+";
                    }
                    else if ("false".equals(value)) {
                        value = "-";
                    }
                    else if (s.value() instanceof Number num && num.intValue() > 0) {
                        value = "+" + value;
                    }

                    yield AscendantSpawners.lang("misc", "concat", value, s.stat().name());
                }
                case SET -> s.value() instanceof Number
                    ? AscendantSpawners.lang("misc", "value_concat", s.stat().name(), value)
                    : ("true".equals(value) ? AscendantSpawners.lang("misc", "on", s.stat().name()) : AscendantSpawners.lang("misc", "off", s.stat().name()));
            };
            int width = font.width(msg);
            boolean hover = mouseX >= left - width && mouseX < left && mouseY >= top && mouseY < top + 9 + 1;
            gfx.text(font, msg, left - font.width(msg), top, hover ? -8355585 : -13421773, false);
            top += 9 + 2;
        }
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, SpawnerModifier recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= -1.0 && mouseX < 9.0 && mouseY >= 13.0 && mouseY < 25.0) {
            tooltip.add(AscendantSpawners.lang("misc", "mainhand"));
        }
        else if (mouseX >= -1.0 && mouseX < 9.0 && mouseY >= 50.0 && mouseY < 62.0 && recipe.offHand().isPresent()) {
            tooltip.add(AscendantSpawners.lang("misc", "offhand"));
            if (!recipe.consumesOffhand()) {
                tooltip.add(AscendantSpawners.lang("misc", "not_consumed").withStyle(ChatFormatting.GRAY));
            }
        }
        else if (mouseX >= 33.0 && mouseX < 49.0 && mouseY >= 30.0 && mouseY < 46.0) {
            tooltip.add(AscendantSpawners.lang("misc", "rclick_spawner"));
        }
        else {
            Font font = Minecraft.getInstance().font;
            int top = 37 - recipe.statModifiers().size() * (9 + 2) / 2 + 2;
            int left = 168;

            for (StatModifier<?> s : recipe.statModifiers()) {
                Component msg = getStatMessage(s);
                int width = font.width(msg);
                if (mouseX >= left - width && mouseX < left && mouseY >= top && mouseY < top + 9 + 1) {
                    tooltip.add(s.stat().name().withStyle(new ChatFormatting[] { ChatFormatting.GREEN, ChatFormatting.UNDERLINE }));
                    tooltip.add(s.stat().desc().withStyle(ChatFormatting.GRAY));
                    if (s.value() instanceof Number && s.mode() == StatModifier.Mode.ADD) {
                        StatModifier<Number> n = (StatModifier<Number>) s;
                        if (s.min().isPresent() || s.max().isPresent()) {
                            tooltip.add(Component.literal(" "));
                        }

                        if (s.min().isPresent()) {
                            tooltip.add(AscendantSpawners.lang("misc", "min_value", n.stat().formatValue(n.min().get())).withStyle(ChatFormatting.GRAY));
                        }

                        if (s.max().isPresent()) {
                            tooltip.add(AscendantSpawners.lang("misc", "max_value", n.stat().formatValue(n.max().get())).withStyle(ChatFormatting.GRAY));
                        }
                    }

                    return;
                }

                top += 9 + 2;
            }
        }
    }

    private static Component getStatMessage(StatModifier<?> s) {
        String value = s.getFormattedValue();

        return switch (s.mode()) {
            case ADD -> {
                if ("true".equals(value)) {
                    value = "+";
                }
                else if ("false".equals(value)) {
                    value = "-";
                }
                else if (s.value() instanceof Number num && num.intValue() > 0) {
                    value = "+" + value;
                }

                yield AscendantSpawners.lang("misc", "concat", value, s.stat().name());
            }
            case SET -> s.value() instanceof Number
                ? AscendantSpawners.lang("misc", "value_concat", s.stat().name(), value)
                : ("true".equals(value) ? AscendantSpawners.lang("misc", "on", s.stat().name()) : AscendantSpawners.lang("misc", "off", s.stat().name()));
        };
    }
}
