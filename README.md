# Cherlsonia Donator Plugin

A comprehensive Minecraft plugin for Paper/Spigot servers that manages donator ranks, special privileges, and exclusive features for supporting players.

## 🏗️ Project Structure

```
src/main/java/org/donator/donator/
├── Main.java                          # Main plugin class
├── DonatorManager.java                # Core donator management system
├── commands/                          # Command system
│   ├── CommandManager.java           # Command manager utility
│   ├── DonatorCommand.java           # /donator command (OP only)
│   ├── InvestorCommand.java          # /investor command
│   ├── CarCommand.java               # /car command
│   └── DonatorTabCompleter.java      # Tab completion for donator command
└── events/                           # Event system
    └── DonatorListener.java          # Event listener for donator features
```

## 🚀 Features

### Donator Rank System
- **Three Donator Types**: `car`, `investor`, and `both` (ultra)
- **Time-based Expiration**: Automatic rank expiration with configurable duration
- **Persistent Storage**: YAML-based data storage for donator information
- **Automatic Cleanup**: Expired ranks are automatically removed

### Special Donator Features

#### 🚗 Car Donator (`donator_car`)
- **Command `/car`** - Summon a special rideable horse
- **Unique Horse**: Custom-named, tamed horse with saddle
- **One Horse Limit**: Players can only have one special horse at a time
- **Death Tracking**: Horse death removes the special horse tag

#### 💎 Investor Donator (`donator_investor`)
- **Command `/investor`** - Receive special diamond armor set
- **Custom Armor**: Named diamond armor with special lore
- **Armor Protection**: Special armor is removed from death drops
- **Slot Validation**: Requires empty armor slots to receive armor
- **Removal Logic**: Removing any piece removes the entire set

#### 🌟 Ultra Donator (`donator_ultra`)
- **Combined Benefits**: Access to both car and investor features
- **All Privileges**: Can use both `/car` and `/investor` commands

### Administrative Features
- **OP-only Management**: Only operators can grant donator ranks
- **Flexible Duration**: Set donator status for any number of days
- **Player Tracking**: Monitor donator status and expiration dates
- **Automatic Tag Management**: Scoreboard tags for permission checking

## 📋 Commands

| Command | Description | Permissions | Usage |
|---------|-------------|-------------|-------|
| `/donator <name> <type> <days>` | Grant donator rank to player | OP only | `/donator PlayerName car 30` |
| `/investor` | Receive special investor armor | `donator_investor` or `donator_ultra` | `/investor` |
| `/car` | Summon special rideable horse | `donator_car` or `donator_ultra` | `/car` |

### Command Details

#### `/donator` Command
- **Permission**: Operator only (`isOp()`)
- **Parameters**:
  - `<name>`: Target player name
  - `<type>`: `car`, `investor`, or `both`
  - `<days>`: Duration in days
- **Examples**:
  - `/donator John car 30` - Grant car donator for 30 days
  - `/donator Alice investor 60` - Grant investor donator for 60 days
  - `/donator Bob both 90` - Grant ultra donator for 90 days

#### `/investor` Command
- **Permission**: Requires `donator_investor` or `donator_ultra` tag
- **Requirements**: Empty armor slots
- **Rewards**: Full set of custom diamond armor
- **Features**:
  - Custom names and lore for each piece
  - Removed from death drops
  - Complete set removal if any piece is removed

#### `/car` Command
- **Permission**: Requires `donator_car` or `donator_ultra` tag
- **Requirements**: No existing special horse
- **Rewards**: Custom tamed horse with saddle
- **Features**:
  - Custom name: "Donator Horse"
  - Automatically tamed and saddled
  - Player mounted automatically
  - Death tracking for replacement

## ⚙️ Configuration

### Data Storage
The plugin uses `donators.yml` for persistent storage:

```yaml
players:
  playername:
    type: "donator_car"
    expires: "2024-12-31"
```

### Plugin Configuration
The `config.yml` contains customizable settings (currently includes waypoint system configuration from the original template).

## 🔧 Technical Implementation

### DonatorManager Class
- **Data Management**: Handles loading/saving donator data
- **Expiration Checking**: Automatic cleanup of expired ranks
- **Tag Management**: Applies and removes scoreboard tags
- **Player Tracking**: Manages donator information per player

### Event System
The `DonatorListener` handles various events:

1. **Armor Removal**: Removes entire special armor set if any piece is removed
2. **Horse Death**: Clears special horse tag when horse dies
3. **Player Join**: Checks and applies donator status on login
4. **Player Death**: Prevents special armor from dropping

### Permission System
- Uses Bukkit's scoreboard tags for permission checking
- Tags: `donator_car`, `donator_investor`, `donator_ultra`
- Automatic tag application/removal based on donator status

## 🎯 Use Cases

### Server Administration
- Reward players for donations with special privileges
- Time-limited donator benefits
- Easy management of donator ranks

### Player Experience
- Exclusive content for supporting players
- Special vehicles and equipment
- Unique gameplay features

### Community Building
- Incentivize server support
- Create exclusive player groups
- Reward loyal community members

## 🔒 Security Features

- **Operator-only Commands**: Administrative commands require OP status
- **Permission Validation**: All donator commands check for proper tags
- **Data Validation**: Input validation for commands and data
- **Safe Storage**: YAML-based configuration with error handling

## 📦 Building and Installation

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- Paper/Spigot server 1.21+

### Building
```bash
mvn clean package
```

The compiled plugin will be in `target/donator-1.0-SNAPSHOT.jar`.

### Installation
1. Build the plugin using Maven
2. Copy the JAR file to your server's `plugins/` folder
3. Restart your server
4. Use `/donator` command to grant donator ranks

## 🚀 Getting Started

### For Server Administrators
1. **Grant Donator Rank**:
   ```
   /donator PlayerName car 30
   ```

2. **Check Donator Status**:
   - Players with donator ranks will have scoreboard tags
   - Use `/scoreboard players list` to see all tags

3. **Manage Expirations**:
   - Expired ranks are automatically removed
   - Check `plugins/donator/donators.yml` for current data

### For Players
1. **Car Donators**: Use `/car` to summon your special horse
2. **Investor Donators**: Use `/investor` to receive special armor
3. **Ultra Donators**: Access both features with `/car` and `/investor`

## 🔄 Extending the Plugin

### Adding New Donator Types
1. Add new type handling in `DonatorCommand.java`
2. Create corresponding command class
3. Update permission checks in event listeners
4. Add new scoreboard tag logic

### Adding New Features
1. Create new command class in `commands/` package
2. Register command in `Main.java`
3. Add to `plugin.yml`
4. Implement permission checking using scoreboard tags

## 📝 License

This plugin is designed for the Cherlsonia server community.

## 🤝 Support

For support, feature requests, or bug reports, please contact the development team.

---

**Note**: This plugin is specifically designed for the Cherlsonia server and includes custom features for donator management and exclusive player benefits.