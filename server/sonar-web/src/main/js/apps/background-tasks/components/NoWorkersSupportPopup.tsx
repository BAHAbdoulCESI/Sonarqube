/*
 * SonarQube
 * Copyright (C) 2009-2023 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
import * as React from 'react';
import Link from '../../../components/common/Link';
import { translate } from '../../../helpers/l10n';

export default function NoWorkersSupportPopup() {
  return (
    <>
      <p className="spacer-bottom">
        <strong>{translate('background_tasks.add_more_workers')}</strong>
      </p>
      <p className="big-spacer-bottom markdown">
        {translate('background_tasks.add_more_workers.text')}
      </p>
      <p>
        <Link
          to="https://www.sonarsource.com/plans-and-pricing/enterprise/?referrer=sonarqube-background-tasks"
          target="_blank"
        >
          {translate('learn_more')}
        </Link>
      </p>
    </>
  );
}
