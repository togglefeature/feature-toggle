<template>
  <v-card>
    <v-card-title>
      Toggles
      <v-spacer></v-spacer>
      <v-btn color="primary" class="mr-3" @click="addToggle">
        <v-icon left>mdi-plus</v-icon>
        Создать
      </v-btn>
      <v-btn outlined color="primary" @click="loadToggles">
        <v-icon left>mdi-refresh</v-icon>
        Обновить
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-row>
        <v-col cols="12" sm="4">
          <v-autocomplete
            v-model="searchToggleTypes"
            :items="toggleTypes"
            clearable
            deletable-chips
            dense
            label="Фильтр по типу"
            multiple
            small-chips
          >
          </v-autocomplete>
        </v-col>
        <v-col cols="12" sm="8">
          <v-text-field
            v-model.lazy="search"
            clearable
            dense
            label="Фильтр по имени"
          ></v-text-field>
        </v-col>
      </v-row>
      <toggle-list
        :toggles="filteredToggles"
        @edit-toggle="editToggleEvent"
        @remove-toggle="removeToggle"
        @switch-toggle="switchToggle"
      ></toggle-list>
    </v-card-text>
    <v-dialog v-model="showCreateDialog" width="50%" scrollable>
      <toggle-edit-card
        :condition-engines="conditionEngines"
        :feature-toggles-types="toggleTypes"
        :toggle="toggle"
        @close="showCreateDialog = false"
        @save="saveToggle"
      ></toggle-edit-card>
    </v-dialog>
  </v-card>
</template>

<script>
import ToggleList from "@/components/toggle/ToggleList";
import ToggleEditCard from "@/components/toggle/ToggleEditCard";

export default {
  name: "TogglesCard",
  components: { ToggleList, ToggleEditCard },
  data: () => ({
    toggles: [],
    conditionEngines: [],
    toggleTypes: [],
    searchToggleTypes: [],
    search: null,
    showCreateDialog: false,
    toggle: {},
    toggleDefault: {
      id: null,
      name: null,
      enabled: false,
      startDate: null,
      endDate: null,
      type: null,
      description: null,
      condition: null,
    },
  }),
  created() {
    Promise.all([
      this.$api.ref.toggleTypes(),
      this.$api.ref.conditionEngines(),
      this.$api.toggle.getToggles(),
    ]).then((response) => {
      this.toggleTypes =
        response[0].status === 200 ? response[0].data.types : [];
      const data =
        response[1].status === 200 ? response[1].data.conditionEngines : [];
      this.conditionEngines = data.map((el, idx) => {
        return { id: idx + 1, name: `${el.type} ${el.language}`, ...el };
      });
      this.toggles =
        response[2].status === 200 ? response[2].data.featureToggles : [];
    });
  },
  computed: {
    filteredToggles() {
      if (this.searchToggleTypes.length > 0) {
        return this.toggles
          .filter((el) => this.searchToggleTypes.includes(el.type))
          .filter(
            (el) =>
              (el.name + "")
                .toLowerCase()
                .startsWith((this.search || "").toLowerCase()) || !this.search
          );
      } else {
        return this.toggles.filter(
          (el) =>
            (el.name + "")
              .toLowerCase()
              .startsWith((this.search || "").toLowerCase()) || !this.search
        );
      }
    },
  },
  methods: {
    loadToggles() {
      this.$api.toggle.getToggles().then((response) => {
        this.toggles =
          response.status === 200 ? response.data.featureToggles : [];
      });
    },
    addToggle() {
      this.toggle = JSON.parse(JSON.stringify(this.toggleDefault));
      this.showCreateDialog = true;
    },
    editToggleEvent(toggle) {
      if (toggle.condition) {
        const cond = this.conditionEngines.find(
          (el) =>
            el.name === `${toggle.condition.type} ${toggle.condition.language}`
        );
        toggle.condition.id = cond.id;
        toggle.condition.name = cond.name;
      }
      this.toggle = JSON.parse(JSON.stringify(toggle));
      this.showCreateDialog = true;
    },
    removeToggle(toggle) {
      this.$api.toggle
        .deleteToggle(toggle.id)
        .then(() => {
          this.toggles = this.toggles.filter((el) => el.id !== toggle.id);
          this.$notifier.showMessage({
            content: `Toggle "${toggle.name}" удален`,
            color: "success",
          });
        })
        .catch((e) => {
          this.$notifier.showMessage({
            content: e.response.data.message,
            color: "error",
          });
        });
    },
    saveToggle: function (toggle) {
      const action = !toggle.id ? "addToggle" : "replaceToggle";
      const msgAction = !toggle.id ? "создан" : "обновлен";
      this.$api.toggle[action](toggle)
        .then((response) => {
          if (!toggle.id) {
            toggle.id = response.data.id;
            this.toggles.push(JSON.parse(JSON.stringify(toggle)));
          } else {
            const idx = this.toggles.findIndex((el) => el.id === toggle.id);
            if (idx >= 0) {
              Object.keys(this.toggles[idx]).forEach((key) => {
                this.toggles[idx][key] = JSON.parse(
                  JSON.stringify(toggle[key])
                );
              });
            }
          }
          this.$notifier.showMessage({
            content: `Toggle "${toggle.name}" ${msgAction}`,
            color: "success",
          });
        })
        .catch((e) => {
          this.$notifier.showMessage({
            content: e.response.data.message,
            color: "error",
          });
        })
        .finally(() => {
          this.showCreateDialog = false;
        });
    },
    switchToggle(toggle) {
      this.$api.toggle
        .switchToggle(toggle.id)
        .then((response) => {
          if (response.data.state === toggle.enabled) {
            this.$notifier.showMessage({
              content: `Toggle "${toggle.name}" ${
                toggle.enabled ? "включен" : "выключен"
              }`,
              color: "success",
            });
          } else {
            toggle.enabled = response.data.state;
            this.$notifier.showMessage({
              content: `Toggle не изменен`,
              color: "primary",
            });
          }
        })
        .catch((e) => {
          this.$notifier.showMessage({
            content: e.response.data.message,
            color: "error",
          });
        });
    },
  },
};
</script>
