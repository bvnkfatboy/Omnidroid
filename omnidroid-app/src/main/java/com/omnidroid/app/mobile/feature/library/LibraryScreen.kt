package com.omnidroid.app.mobile.feature.library

import android.content.Context
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ScreenRotation
import androidx.compose.material.icons.outlined.ZoomIn
import androidx.compose.material.icons.outlined.ZoomOut
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.omnidroid.R
import com.omnidroid.app.mobile.shared.compose.ui.GameCardInfoStyle
import com.omnidroid.app.mobile.shared.compose.ui.HomeChromeBackground
import com.omnidroid.app.mobile.shared.compose.ui.OmnidroidEmptyView
import com.omnidroid.app.mobile.shared.compose.ui.OmnidroidGameCard
import com.omnidroid.app.mobile.shared.compose.ui.OmnidroidGameImage
import com.omnidroid.app.mobile.shared.compose.ui.LibraryGameCardAspectRatio
import com.omnidroid.app.mobile.shared.compose.ui.LibraryNeonGreen
import com.omnidroid.app.mobile.shared.compose.ui.LocalAdaptiveLayout
import com.omnidroid.app.mobile.shared.compose.ui.ThreeDsTextDark
import com.omnidroid.app.mobile.shared.compose.ui.ThreeDsTextGray
import com.omnidroid.app.mobile.shared.compose.ui.ThreeDsTileBorder
import com.omnidroid.app.mobile.shared.compose.ui.ThreeDsTileWhite
import com.omnidroid.app.mobile.shared.compose.ui.avoidDisplayCutout
import com.omnidroid.app.mobile.shared.compose.ui.threeDsStripedBackground
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import com.omnidroid.app.mobile.shared.controller.LocalControllerNavigation
import com.omnidroid.app.mobile.shared.controller.controllerFocusGlow
import com.omnidroid.app.mobile.shared.controller.reportControllerGame
import com.omnidroid.app.utils.games.GameUtils
import com.omnidroid.lib.library.MetaSystemID
import com.omnidroid.lib.library.db.entity.Game
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Home
import com.omnidroid.app.mobile.shared.compose.ui.ThreeDsSelectedBorder

val LibrarySidebarWidth = 80.dp
val LibrarySidebarButtonSize = 48.dp
val LibrarySidebarInset = (LibrarySidebarWidth - LibrarySidebarButtonSize) / 2

fun libraryGridRows(zoomDensity: Int, availableHeight: Dp, isSquare: Boolean = false): Int {
    if (isSquare) {
        return when (zoomDensity) {
            0 -> 2
            1 -> 3
            else -> 4
        }
    }
    val h = availableHeight.value
    return when {
        h < 440f -> {
            when (zoomDensity) {
                0 -> 1
                1 -> 2
                else -> 3
            }
        }
        h < 640f -> {
            when (zoomDensity) {
                0 -> 2
                1 -> 3
                else -> 5
            }
        }
        h < 880f -> {
            when (zoomDensity) {
                0 -> 3
                1 -> 5
                else -> 7
            }
        }
        else -> {
            when (zoomDensity) {
                0 -> 3
                1 -> (h / 140f).toInt().coerceIn(5, 6)
                else -> (h / 90f).toInt().coerceIn(7, 9)
            }
        }
    }
}

fun libraryPortraitColumns(zoomDensity: Int, availableWidth: Dp, isSquare: Boolean = false): Int {
    if (isSquare) {
        return when (zoomDensity) {
            0 -> 3
            1 -> 4
            else -> 5
        }
    }
    val w = availableWidth.value
    val cardWidth =
        when (zoomDensity) {
            0 -> if (w < 400f) 110f else 150f
            1 -> if (w < 400f) 85f else 112f
            else -> if (w < 400f) 65f else 84f
        }
    return (w / cardWidth).toInt().coerceIn(2, 6)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: LibraryViewModel,
    onGameClick: (Game) -> Unit,
    onContinueClick: (Game) -> Unit,
    onGameLongClick: (Game) -> Unit,
    onAddConsole: () -> Unit,
    controllerConnected: Boolean = false,
    portraitColumnWidth: Dp = Dp.Unspecified,
) {
    val state = viewModel.state.collectAsState().value
    val games = viewModel.games.collectAsLazyPagingItems()
    var startupTarget by remember { mutableStateOf<MetaSystemID?>(null) }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .threeDsStripedBackground(),
    ) {
        if (state.portrait) {
            Column(
                modifier =
                    Modifier
                        .then(
                            if (portraitColumnWidth != Dp.Unspecified) {
                                Modifier.width(portraitColumnWidth)
                            } else {
                                Modifier.fillMaxWidth()
                            },
                        )
                        .fillMaxHeight()
                        .align(Alignment.TopCenter),
            ) {
                LibraryGames(
                    modifier = Modifier.weight(1f),
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    state = state,
                    games = games,
                    onGameClick = onGameClick,
                    onContinueClick = onContinueClick,
                    onGameLongClick = onGameLongClick,
                    onSync = { viewModel.syncLibrary(it) },
                    onZoomDensityChange = { viewModel.setZoomDensity(it) },
                    onLayoutChange = { viewModel.setLayout(it) },
                    onPortraitChange = { viewModel.setPortrait(it) },
                    requestInitialFocus = controllerConnected,
                    showZoomBar = !controllerConnected,
                    portrait = true,
                )
                PortraitConsoleBar(
                    filter = state.filter,
                    systems = state.sidebarSystems,
                    startupFilter = state.startupFilter,
                    onFilterSelected = { viewModel.selectFilter(it) },
                    onConsoleLongClick = { startupTarget = it },
                    onAddConsole = onAddConsole,
                )
            }
        } else {
            // The sidebar rail is pinned to the screen edge, so in landscape it runs under the
            // camera cutout. Inset the whole row rather than the rail itself, which is only
            // 80.dp wide and could not absorb the inset without clipping its buttons.
            Row(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .avoidDisplayCutout(WindowInsetsSides.Horizontal),
            ) {
                LibrarySidebar(
                    modifier = Modifier,
                    filter = state.filter,
                    systems = state.sidebarSystems,
                    startupFilter = state.startupFilter,
                    onFilterSelected = { viewModel.selectFilter(it) },
                    onConsoleLongClick = { startupTarget = it },
                    onAddConsole = onAddConsole,
                )
                LibraryGames(
                    modifier = Modifier.weight(1f),
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    state = state,
                    games = games,
                    onGameClick = onGameClick,
                    onContinueClick = onContinueClick,
                    onGameLongClick = onGameLongClick,
                    onSync = { viewModel.syncLibrary(it) },
                    onZoomDensityChange = { viewModel.setZoomDensity(it) },
                    onLayoutChange = { viewModel.setLayout(it) },
                    onPortraitChange = { viewModel.setPortrait(it) },
                    requestInitialFocus = controllerConnected,
                    showZoomBar = !controllerConnected,
                    portrait = false,
                )
            }
        }

        startupTarget?.let { target ->
            SetStartupScreenDialog(
                console = target,
                isStartupScreen = state.startupFilter == LibraryFilter.System(target),
                onConfirm = {
                    viewModel.setStartupFilter(
                        if (state.startupFilter == LibraryFilter.System(target)) {
                            LibraryFilter.All
                        } else {
                            LibraryFilter.System(target)
                        },
                    )
                    startupTarget = null
                },
                onDismiss = { startupTarget = null },
            )
        }
    }
}

@Composable
private fun LibrarySidebar(
    modifier: Modifier = Modifier,
    filter: LibraryFilter,
    systems: List<MetaSystemID>,
    startupFilter: LibraryFilter,
    onFilterSelected: (LibraryFilter) -> Unit,
    onConsoleLongClick: (MetaSystemID) -> Unit,
    onAddConsole: () -> Unit,
) {
    val adaptiveSpec = LocalAdaptiveLayout.current
    Column(
        modifier =
            modifier
                .fillMaxHeight()
                .width(adaptiveSpec.sidebarWidth)
                .padding(vertical = if (adaptiveSpec.isCompact) 6.dp else 12.dp)
                .focusGroup(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SidebarIconButton(
            selected = filter is LibraryFilter.Favorites,
            icon = if (filter is LibraryFilter.Favorites) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = stringResource(R.string.favorites),
            onClick = { onFilterSelected(LibraryFilter.Favorites) },
            pinned = startupFilter is LibraryFilter.Favorites,
        )
        Spacer(modifier = Modifier.height(4.dp))
        SidebarIconButton(
            selected = filter is LibraryFilter.All,
            icon = Icons.Filled.GridView,
            contentDescription = stringResource(R.string.show_all),
            onClick = { onFilterSelected(LibraryFilter.All) },
        )
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(
            modifier = Modifier.width(if (adaptiveSpec.isCompact) 20.dp else 28.dp),
            color = Color.White.copy(alpha = 0.12f),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            systems.forEach { system ->
                SidebarSystemLogoButton(
                    meta = system,
                    selected = (filter as? LibraryFilter.System)?.metaSystemID == system,
                    pinned = startupFilter == LibraryFilter.System(system),
                    onClick = { onFilterSelected(LibraryFilter.System(system)) },
                    onLongClick = { onConsoleLongClick(system) },
                )
            }
            SidebarIconButton(
                selected = false,
                icon = Icons.Filled.Add,
                contentDescription = stringResource(R.string.title_add_console),
                onClick = onAddConsole,
            )
        }
    }
}

@Composable
private fun SidebarIconButton(
    selected: Boolean,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    pinned: Boolean = false,
) {
    val adaptiveSpec = LocalAdaptiveLayout.current
    Surface(
        onClick = onClick,
        modifier = Modifier.size(adaptiveSpec.sidebarButtonSize).controllerFocusGlow(CircleShape),
        shape = CircleShape,
        color = if (selected) MaterialTheme.colorScheme.secondaryContainer else ThreeDsTileWhite,
        shadowElevation = 6.dp,
        tonalElevation = 0.dp,
        border = sidebarSelectionBorder(selected),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(adaptiveSpec.sidebarIconSize),
                tint = if (selected) LibraryNeonGreen else ThreeDsTextGray,
            )
            StartupBadge(visible = pinned)
        }
    }
}

@Composable
private fun BoxScope.StartupBadge(visible: Boolean) {
    if (!visible) return
    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = null,
        modifier = Modifier.align(Alignment.TopEnd).size(14.dp).padding(1.dp),
        tint = LibraryNeonGreen,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SidebarSystemLogoButton(
    meta: MetaSystemID,
    selected: Boolean,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    pinned: Boolean = false,
) {
    val adaptiveSpec = LocalAdaptiveLayout.current
    Surface(
        modifier =
            Modifier
                .size(adaptiveSpec.sidebarButtonSize)
                .controllerFocusGlow(CircleShape)
                .clip(CircleShape)
                .combinedClickable(
                    role = Role.Button,
                    onClick = onClick,
                    onLongClick = onLongClick,
                ),
        shape = CircleShape,
        color = Color(meta.color()),
        shadowElevation = 6.dp,
        tonalElevation = 0.dp,
        border = sidebarSelectionBorder(selected),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = meta.imageResId),
                contentDescription = stringResource(id = meta.titleResId),
                modifier = Modifier.fillMaxSize(0.84f),
                contentScale = ContentScale.Fit,
            )
            StartupBadge(visible = pinned)
        }
    }
}

private fun sidebarSelectionBorder(selected: Boolean) =
    BorderStroke(
        width = if (selected) 2.dp else 1.dp,
        color = if (selected) LibraryNeonGreen else ThreeDsTileBorder,
    )

@Composable
private fun PortraitConsoleBar(
    filter: LibraryFilter,
    systems: List<MetaSystemID>,
    startupFilter: LibraryFilter,
    onFilterSelected: (LibraryFilter) -> Unit,
    onConsoleLongClick: (MetaSystemID) -> Unit,
    onAddConsole: () -> Unit,
) {
    val adaptiveSpec = LocalAdaptiveLayout.current
    val listState = rememberLazyListState()
    val selectedSystemIndex =
        (filter as? LibraryFilter.System)?.let { systems.indexOf(it.metaSystemID) } ?: -1
    LaunchedEffect(selectedSystemIndex, systems.size) {
        if (selectedSystemIndex >= 0) {
            runCatching { listState.animateScrollToItem(selectedSystemIndex) }
        }
    }
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(adaptiveSpec.consoleBarHeight)
                .padding(horizontal = if (adaptiveSpec.isCompact) 8.dp else 12.dp)
                .focusGroup(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SidebarIconButton(
            selected = filter is LibraryFilter.Favorites,
            icon = if (filter is LibraryFilter.Favorites) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = stringResource(R.string.favorites),
            onClick = { onFilterSelected(LibraryFilter.Favorites) },
            pinned = startupFilter is LibraryFilter.Favorites,
        )
        Spacer(modifier = Modifier.width(8.dp))
        SidebarIconButton(
            selected = filter is LibraryFilter.All,
            icon = Icons.Filled.GridView,
            contentDescription = stringResource(R.string.show_all),
            onClick = { onFilterSelected(LibraryFilter.All) },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier =
                Modifier
                    .padding(horizontal = 2.dp)
                    .width(1.dp)
                    .height(28.dp)
                    .background(ThreeDsTextDark.copy(alpha = 0.12f)),
        )
        Spacer(modifier = Modifier.width(8.dp))
        LazyRow(
            state = listState,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(systems, key = { it.name }) { system ->
                SidebarSystemLogoButton(
                    meta = system,
                    selected = (filter as? LibraryFilter.System)?.metaSystemID == system,
                    pinned = startupFilter == LibraryFilter.System(system),
                    onClick = { onFilterSelected(LibraryFilter.System(system)) },
                    onLongClick = { onConsoleLongClick(system) },
                )
            }
            item(key = "add") {
                SidebarIconButton(
                    selected = false,
                    icon = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.title_add_console),
                    onClick = onAddConsole,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun LibraryGames(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: LibraryViewModel.UiState,
    games: LazyPagingItems<Game>,
    onGameClick: (Game) -> Unit,
    onContinueClick: (Game) -> Unit,
    onGameLongClick: (Game) -> Unit,
    onSync: (Context) -> Unit,
    onZoomDensityChange: (Int) -> Unit,
    onLayoutChange: (LibraryLayout) -> Unit,
    onPortraitChange: (Boolean) -> Unit,
    requestInitialFocus: Boolean = false,
    showZoomBar: Boolean = true,
    portrait: Boolean = false,
) {
    val context = LocalContext.current
    val firstItemRequester = remember { FocusRequester() }
    val navigation = LocalControllerNavigation.current
    navigation?.contentFocusRequester = firstItemRequester
    val continueGame = state.continueGame
    val showContinue =
        state.filter is LibraryFilter.All &&
            state.searchQuery.isBlank() &&
            continueGame != null
    LaunchedEffect(requestInitialFocus, games.itemCount, showContinue, state.layout) {
        if (requestInitialFocus && (games.itemCount > 0 || showContinue)) {
            runCatching { firstItemRequester.requestFocus() }
        }
    }
    val isRefreshing = games.loadState.refresh is LoadState.Loading
    val isEmpty = games.itemCount == 0 && !showContinue && !isRefreshing
    val showSyncEmpty =
        isEmpty &&
            state.searchQuery.isBlank() &&
            state.filter !is LibraryFilter.Favorites
    val infoStyle =
        if (portrait) {
            if (state.zoomDensity >= 2) GameCardInfoStyle.MINIMAL else GameCardInfoStyle.OVERLAY
        } else {
            when (state.zoomDensity) {
                0 -> GameCardInfoStyle.BELOW
                1 -> GameCardInfoStyle.OVERLAY
                else -> GameCardInfoStyle.MINIMAL
            }
        }
    val showViewBar = games.itemCount > 0 || showContinue || portrait

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
        ) {
            when {
                isRefreshing && games.itemCount == 0 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                showSyncEmpty -> {
                    LibrarySyncEmpty(
                        modifier = Modifier,
                        scanning = state.operationInProgress,
                        onSync = { onSync(context) },
                    )
                }
                isEmpty -> {
                    OmnidroidEmptyView(modifier = Modifier)
                }
                else -> {
                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val featured = continueGame.takeIf { showContinue }
                    if (state.layout == LibraryLayout.HOME) {
                        ThreeDsHomeCarousel(
                            modifier = Modifier.fillMaxSize(),
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            games = games,
                            featured = featured,
                            firstItemRequester = firstItemRequester,
                            onGameClick = onGameClick,
                            onContinueClick = onContinueClick,
                            onGameLongClick = onGameLongClick,
                        )
                    } else if (state.layout == LibraryLayout.LIST) {
                        LazyColumn(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .focusGroup(),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            if (featured != null) {
                                item(key = "continue-${featured.id}") {
                                    LibraryGameRow(
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        game = featured,
                                        modifier = Modifier.focusRequester(firstItemRequester),
                                        continueAction = true,
                                        onClick = { onContinueClick(featured) },
                                        onLongClick = { onGameLongClick(featured) },
                                    )
                                }
                            }
                            items(
                                count = games.itemCount,
                                key = { index -> games.peek(index)?.id ?: index },
                            ) { index ->
                                val game = games[index] ?: return@items
                                LibraryGameRow(
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    game = game,
                                    modifier =
                                        if (!showContinue && index == 0) {
                                            Modifier.focusRequester(firstItemRequester)
                                        } else {
                                            Modifier
                                        },
                                    continueAction = false,
                                    onClick = { onGameClick(game) },
                                    onLongClick = { onGameLongClick(game) },
                                )
                            }
                        }
                    } else if (portrait) {
                        val adaptiveSpec = LocalAdaptiveLayout.current
                        val columns = libraryPortraitColumns(state.zoomDensity, maxWidth, adaptiveSpec.isSquare)
                        LazyVerticalGrid(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .focusGroup(),
                            columns = GridCells.Fixed(columns),
                            contentPadding = PaddingValues(horizontal = adaptiveSpec.gridSpacing, vertical = adaptiveSpec.gridSpacing),
                            horizontalArrangement = Arrangement.spacedBy(adaptiveSpec.gridSpacing),
                            verticalArrangement = Arrangement.spacedBy(adaptiveSpec.gridSpacing),
                        ) {
                            if (featured != null) {
                                item(key = "continue-${featured.id}") {
                                    LibraryGameCardItem(
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        game = featured,
                                        modifier = Modifier.focusRequester(firstItemRequester),
                                        continueAction = true,
                                        infoStyle = infoStyle,
                                        matchHeight = false,
                                        onClick = { onContinueClick(featured) },
                                        onLongClick = { onGameLongClick(featured) },
                                    )
                                }
                            }
                            items(
                                count = games.itemCount,
                                key = { index -> games.peek(index)?.id ?: index },
                            ) { index ->
                                val game = games[index] ?: return@items
                                LibraryGameCardItem(
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    game = game,
                                    modifier =
                                        if (!showContinue && index == 0) {
                                             Modifier.focusRequester(firstItemRequester)
                                        } else {
                                            Modifier
                                        },
                                    continueAction = false,
                                    infoStyle = infoStyle,
                                    matchHeight = false,
                                    onClick = { onGameClick(game) },
                                    onLongClick = { onGameLongClick(game) },
                                )
                            }
                        }
                    } else {
                        val adaptiveSpec = LocalAdaptiveLayout.current
                        val rows = libraryGridRows(state.zoomDensity, maxHeight, adaptiveSpec.isSquare)
                        LazyHorizontalGrid(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .focusGroup(),
                            rows = GridCells.Fixed(rows),
                            contentPadding = PaddingValues(horizontal = adaptiveSpec.gridSpacing + 4.dp, vertical = adaptiveSpec.gridSpacing),
                            horizontalArrangement = Arrangement.spacedBy(adaptiveSpec.gridSpacing),
                            verticalArrangement = Arrangement.spacedBy(adaptiveSpec.gridSpacing),
                        ) {
                            if (featured != null) {
                                item(key = "continue-${featured.id}") {
                                    LibraryGameCardItem(
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        game = featured,
                                        modifier = Modifier.focusRequester(firstItemRequester),
                                        continueAction = true,
                                        infoStyle = infoStyle,
                                        onClick = { onContinueClick(featured) },
                                        onLongClick = { onGameLongClick(featured) },
                                    )
                                }
                            }

                            items(
                                count = games.itemCount,
                                key = { index -> games.peek(index)?.id ?: index },
                            ) { index ->
                                val game = games[index] ?: return@items
                                LibraryGameCardItem(
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    game = game,
                                    modifier =
                                        if (!showContinue && index == 0) {
                                            Modifier.focusRequester(firstItemRequester)
                                        } else {
                                            Modifier
                                        },
                                    continueAction = false,
                                    infoStyle = infoStyle,
                                    onClick = { onGameClick(game) },
                                    onLongClick = { onGameLongClick(game) },
                                )
                            }
                        }
                    }
                    }
                }
            }
        }
        if (showViewBar) {
            LibraryViewBar(
                layout = state.layout,
                density = state.zoomDensity,
                portrait = portrait,
                showZoom = showZoomBar && state.layout == LibraryLayout.GRID,
                onLayoutChange = onLayoutChange,
                onPortraitChange = onPortraitChange,
                onDensityChange = onZoomDensityChange,
                modifier = Modifier,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun LibraryGameCardItem(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    game: Game,
    modifier: Modifier = Modifier,
    continueAction: Boolean,
    infoStyle: GameCardInfoStyle,
    matchHeight: Boolean = true,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val sizeModifier =
        if (matchHeight) {
            Modifier
                .fillMaxHeight()
                .then(
                    if (infoStyle == GameCardInfoStyle.BELOW) {
                        Modifier
                    } else {
                        Modifier.aspectRatio(LibraryGameCardAspectRatio, matchHeightConstraintsFirst = true)
                    },
                )
        } else {
            Modifier.fillMaxWidth()
        }
    val cornerAnim by animatedVisibilityScope.transition.animateDp(
        label = "coverCorner-${game.id}",
        transitionSpec = { tween(durationMillis = 400, easing = FastOutSlowInEasing) },
    ) { targetState ->
        if (targetState == EnterExitState.Visible) 4.dp else 16.dp
    }
    val coverSharedModifier =
        with(sharedTransitionScope) {
            Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "game-cover-${game.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                enter = EnterTransition.None,
                exit = ExitTransition.None,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 400, easing = FastOutSlowInEasing)
                },
                clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(cornerAnim)),
            )
        }
    OmnidroidGameCard(
        modifier = modifier.then(sizeModifier),
        coverModifier = coverSharedModifier,
        game = game,
        continueAction = continueAction,
        infoStyle = infoStyle,
        fillCard = matchHeight,
        cornerRadius = cornerAnim,
        onClick = onClick,
        onLongClick = onLongClick,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ThreeDsHomeCarousel(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    games: LazyPagingItems<Game>,
    featured: Game?,
    firstItemRequester: FocusRequester,
    onGameClick: (Game) -> Unit,
    onContinueClick: (Game) -> Unit,
    onGameLongClick: (Game) -> Unit,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val featuredOffset = if (featured != null) 1 else 0
    val centeredIndex by remember {
        derivedStateOf {
            val info = listState.layoutInfo
            val viewportCenter = (info.viewportStartOffset + info.viewportEndOffset) / 2
            info.visibleItemsInfo
                .minByOrNull { abs(it.offset + it.size / 2 - viewportCenter) }
                ?.index ?: 0
        }
    }
    val canScrollBack by remember { derivedStateOf { listState.canScrollBackward } }
    val canScrollForward by remember { derivedStateOf { listState.canScrollForward } }
    BoxWithConstraints(modifier = modifier) {
        val tileHeight = maxHeight * 0.5f
        val tileWidth = tileHeight * LibraryGameCardAspectRatio
        val horizontalPadding = ((maxWidth - tileWidth) / 2f).coerceAtLeast(24.dp)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            ) {
                LazyRow(
                    state = listState,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .focusGroup(),
                    contentPadding = PaddingValues(horizontal = horizontalPadding),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (featured != null) {
                        item(key = "continue-${featured.id}") {
                            ThreeDsCarouselTile(
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope,
                                game = featured,
                                isCentered = centeredIndex == 0,
                                tileWidth = tileWidth,
                                continueAction = true,
                                modifier = Modifier.focusRequester(firstItemRequester),
                                onClick = { onContinueClick(featured) },
                                onLongClick = { onGameLongClick(featured) },
                            )
                        }
                    }
                    items(
                        count = games.itemCount,
                        key = { index -> games.peek(index)?.id ?: index },
                    ) { index ->
                        val game = games[index] ?: return@items
                        ThreeDsCarouselTile(
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            game = game,
                            isCentered = centeredIndex == index + featuredOffset,
                            tileWidth = tileWidth,
                            continueAction = false,
                            modifier =
                                if (featured == null && index == 0) {
                                    Modifier.focusRequester(firstItemRequester)
                                } else {
                                    Modifier
                                },
                            onClick = { onGameClick(game) },
                            onLongClick = { onGameLongClick(game) },
                        )
                    }
                }
                if (canScrollBack) {
                    ThreeDsCarouselArrow(
                        icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.library_carousel_previous),
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem((centeredIndex - 1).coerceAtLeast(0))
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                }
                if (canScrollForward) {
                    ThreeDsCarouselArrow(
                        icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.library_carousel_next),
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem(centeredIndex + 1)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterEnd),
                    )
                }
            }
            val centeredGame =
                when {
                    featured != null && centeredIndex == 0 -> featured
                    centeredIndex - featuredOffset >= 0 -> games[centeredIndex - featuredOffset]
                    else -> null
                }
            centeredGame?.let { game ->
                val consoleName =
                    remember(game.id) { GameUtils.getSystemShortName(context, game) }
                Column(
                    modifier =
                        Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 8.dp,
                            bottom = 12.dp,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = game.displayName,
                        style =
                            MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = ThreeDsTextDark,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = consoleName,
                        style = MaterialTheme.typography.labelMedium,
                        color = ThreeDsTextGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ThreeDsCarouselTile(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    game: Game,
    isCentered: Boolean,
    tileWidth: Dp,
    continueAction: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val scale by animateFloatAsState(
        targetValue = if (isCentered) 1f else 0.86f,
        animationSpec =
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow,
            ),
        label = "threeDsTileScale-${game.id}",
    )
    val coverSharedModifier =
        with(sharedTransitionScope) {
            Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "game-cover-${game.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                enter = EnterTransition.None,
                exit = ExitTransition.None,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 400, easing = FastOutSlowInEasing)
                },
                clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(16.dp)),
            )
        }
    OmnidroidGameCard(
        modifier =
            modifier
                .width(tileWidth)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = if (isCentered) 1f else 0.85f
                },
        coverModifier = coverSharedModifier,
        game = game,
        continueAction = continueAction,
        infoStyle = GameCardInfoStyle.MINIMAL,
        cornerRadius = 16.dp,
        selected = isCentered,
        onClick = onClick,
        onLongClick = onLongClick,
    )
}

@Composable
private fun ThreeDsCarouselArrow(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier =
            modifier
                .size(36.dp)
                .controllerFocusGlow(CircleShape),
        shape = CircleShape,
        color = ThreeDsTileWhite.copy(alpha = 0.92f),
        shadowElevation = 4.dp,
        tonalElevation = 0.dp,
        border = BorderStroke(1.dp, ThreeDsTileBorder),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(22.dp),
                tint = ThreeDsTextDark,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalFoundationApi::class)
@Composable
private fun LibraryGameRow(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    game: Game,
    modifier: Modifier = Modifier,
    continueAction: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val context = LocalContext.current
    val subtitle =
        remember(game.id, game.systemId, game.developer, game.title, game.customName) {
            GameUtils.getGameSubtitle(context, game)
        }
    val rowCorner = 10.dp
    val coverInset = 4.dp
    val shape = RoundedCornerShape(rowCorner)
    val cornerAnim by animatedVisibilityScope.transition.animateDp(
        label = "coverCorner-${game.id}",
        transitionSpec = { tween(durationMillis = 400, easing = FastOutSlowInEasing) },
    ) { targetState ->
        if (targetState == EnterExitState.Visible) (rowCorner - coverInset).coerceAtLeast(0.dp) else 16.dp
    }
    val coverSharedModifier =
        with(sharedTransitionScope) {
            Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "game-cover-${game.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                enter = EnterTransition.None,
                exit = ExitTransition.None,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 400, easing = FastOutSlowInEasing)
                },
                clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(cornerAnim)),
            )
        }
    val adaptiveSpec = LocalAdaptiveLayout.current
    val thumbHeight = adaptiveSpec.listThumbHeight
    val thumbWidth = thumbHeight * LibraryGameCardAspectRatio
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .controllerFocusGlow(shape)
                .reportControllerGame(game)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick,
                ),
        shape = shape,
        color = ThreeDsTileWhite,
        shadowElevation = 4.dp,
        tonalElevation = 0.dp,
        border = BorderStroke(1.dp, ThreeDsTileBorder),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(thumbHeight + coverInset * 2)
                    .padding(end = coverInset),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OmnidroidGameImage(
                modifier =
                    Modifier
                        .padding(start = coverInset)
                        .size(thumbWidth, thumbHeight)
                        .clip(RoundedCornerShape(cornerAnim))
                        .then(coverSharedModifier),
                game = game,
                aspectRatio = null,
            )
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
            ) {
                Text(
                    text = game.displayName,
                    style =
                        MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
            when {
                continueAction -> {
                    Surface(
                        shape = RoundedCornerShape((rowCorner - coverInset).coerceAtLeast(0.dp)),
                        color = LibraryNeonGreen,
                    ) {
                        Text(
                            text = stringResource(R.string.continue_playing),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style =
                                MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ),
                            color = Color.White,
                            maxLines = 1,
                        )
                    }
                }
                game.isFavorite -> {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.favorites),
                        modifier = Modifier.size(16.dp),
                        tint = LibraryNeonGreen,
                    )
                }
            }
        }
    }
}

@Composable
private fun LibraryViewBar(
    layout: LibraryLayout,
    density: Int,
    portrait: Boolean,
    showZoom: Boolean,
    onLayoutChange: (LibraryLayout) -> Unit,
    onPortraitChange: (Boolean) -> Unit,
    onDensityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (portrait) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(start = 8.dp, end = 8.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showZoom) {
                LibraryZoomControls(
                    density = density,
                    onDensityChange = onDensityChange,
                    trackWidth = null,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            LibraryViewModeButtons(
                layout = layout,
                portrait = portrait,
                onLayoutChange = onLayoutChange,
                onPortraitChange = onPortraitChange,
            )
        }
    } else {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(bottom = 6.dp),
        ) {
            LibraryViewModeButtons(
                layout = layout,
                portrait = portrait,
                onLayoutChange = onLayoutChange,
                onPortraitChange = onPortraitChange,
                modifier =
                    Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp),
            )
            if (showZoom) {
                LibraryZoomControls(
                    density = density,
                    onDensityChange = onDensityChange,
                    trackWidth = 168.dp,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun LibraryViewModeButtons(
    layout: LibraryLayout,
    portrait: Boolean,
    onLayoutChange: (LibraryLayout) -> Unit,
    onPortraitChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LibraryLayoutToggle(
            layout = layout,
            onLayoutChange = onLayoutChange,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier =
                Modifier
                    .size(28.dp)
                    .background(ThreeDsTileWhite, CircleShape)
                    .border(
                        BorderStroke(1.dp, if (portrait) LibraryNeonGreen else ThreeDsTileBorder),
                        CircleShape,
                    ),
        ) {
            LayoutToggleIcon(
                selected = portrait,
                icon = Icons.Outlined.ScreenRotation,
                boxSize = 28.dp,
                iconSize = 18.dp,
                contentDescription =
                    stringResource(
                        if (portrait) R.string.library_view_landscape else R.string.library_view_portrait,
                    ),
                onClick = { onPortraitChange(!portrait) },
            )
        }
    }
}

@Composable
private fun LibraryLayoutToggle(
    layout: LibraryLayout,
    onLayoutChange: (LibraryLayout) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .height(28.dp)
                .background(ThreeDsTileWhite, CircleShape)
                .border(BorderStroke(1.dp, ThreeDsTileBorder), CircleShape)
                .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LayoutToggleIcon(
            selected = layout == LibraryLayout.HOME,
            icon = Icons.Filled.Home,
            contentDescription = stringResource(R.string.library_view_home),
            onClick = { onLayoutChange(LibraryLayout.HOME) },
        )
        LayoutToggleIcon(
            selected = layout == LibraryLayout.GRID,
            icon = Icons.Filled.GridView,
            contentDescription = stringResource(R.string.library_view_grid),
            onClick = { onLayoutChange(LibraryLayout.GRID) },
        )
        LayoutToggleIcon(
            selected = layout == LibraryLayout.LIST,
            icon = Icons.AutoMirrored.Outlined.ViewList,
            contentDescription = stringResource(R.string.library_view_list),
            onClick = { onLayoutChange(LibraryLayout.LIST) },
        )
    }
}

@Composable
private fun LayoutToggleIcon(
    selected: Boolean,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    boxSize: Dp = 24.dp,
    iconSize: Dp = 16.dp,
) {
    Box(
        modifier =
            Modifier
                .size(boxSize)
                .controllerFocusGlow(CircleShape)
                .background(
                    color = if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                    shape = CircleShape,
                )
                .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = if (selected) LibraryNeonGreen else ThreeDsTextGray,
        )
    }
}

@Composable
private fun LibraryZoomControls(
    density: Int,
    onDensityChange: (Int) -> Unit,
    trackWidth: Dp?,
    modifier: Modifier = Modifier,
) {
    val zoomOutEnabled = density < LibraryViewModel.MAX_ZOOM_DENSITY
    val zoomInEnabled = density > 0
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .controllerFocusGlow(CircleShape)
                    .clickable(enabled = zoomOutEnabled) {
                        onDensityChange(density + 1)
                    },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.ZoomOut,
                contentDescription = stringResource(R.string.library_zoom_out),
                modifier = Modifier.size(18.dp),
                tint = ThreeDsTextGray.copy(alpha = if (zoomOutEnabled) 1f else 0.35f),
            )
        }
        ZoomTrack(
            position = LibraryViewModel.MAX_ZOOM_DENSITY - density,
            onPositionChange = { onDensityChange(LibraryViewModel.MAX_ZOOM_DENSITY - it) },
            modifier =
                Modifier
                    .then(
                        if (trackWidth != null) {
                            Modifier.width(trackWidth)
                        } else {
                            Modifier.weight(1f)
                        },
                    )
                    .height(24.dp)
                    .padding(horizontal = 8.dp)
                    .controllerFocusGlow()
                    .focusable()
                    .onKeyEvent { event ->
                        if (event.type != KeyEventType.KeyUp) return@onKeyEvent false
                        when (event.key) {
                            Key.DirectionLeft -> {
                                if (zoomOutEnabled) onDensityChange(density + 1)
                                true
                            }
                            Key.DirectionRight -> {
                                if (zoomInEnabled) onDensityChange(density - 1)
                                true
                            }
                            else -> false
                        }
                    },
        )
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .controllerFocusGlow(CircleShape)
                    .clickable(enabled = zoomInEnabled) {
                        onDensityChange(density - 1)
                    },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.ZoomIn,
                contentDescription = stringResource(R.string.library_zoom_in),
                modifier = Modifier.size(18.dp),
                tint = ThreeDsTextGray.copy(alpha = if (zoomInEnabled) 1f else 0.35f),
            )
        }
    }
}

@Composable
private fun ZoomTrack(
    position: Int,
    onPositionChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val thumbColor = LibraryNeonGreen
    val trackColor = ThreeDsSelectedBorder
    Box(
        modifier =
            modifier.pointerInput(Unit) {
                val snap = { x: Float ->
                    val pad = size.height / 2f
                    val usable = (size.width - 2f * pad).coerceAtLeast(1f)
                    val t = ((x - pad) / usable).coerceIn(0f, 1f)
                    onPositionChange((t * LibraryViewModel.MAX_ZOOM_DENSITY).roundToInt())
                }
                awaitEachGesture {
                    val down = awaitFirstDown()
                    snap(down.position.x)
                    drag(down.id) { change ->
                        snap(change.position.x)
                        change.consume()
                    }
                }
            },
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val pad = size.height / 2f
            val y = size.height / 2f
            val start = pad
            val end = size.width - pad
            drawLine(
                color = trackColor,
                start = Offset(start, y),
                end = Offset(end, y),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round,
            )
            val cx = start + (end - start) * (position / LibraryViewModel.MAX_ZOOM_DENSITY.toFloat())
            drawCircle(
                color = thumbColor,
                radius = 7.dp.toPx(),
                center = Offset(cx, y),
            )
        }
    }
}

@Composable
private fun LibrarySyncEmpty(
    modifier: Modifier,
    scanning: Boolean,
    onSync: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        FilledTonalButton(
            onClick = { if (!scanning) onSync() },
            modifier = Modifier.controllerFocusGlow(),
        ) {
            RescanIcon(
                spinning = scanning,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(
                    if (scanning) R.string.library_scanning else R.string.rescan,
                ),
            )
        }
    }
}
